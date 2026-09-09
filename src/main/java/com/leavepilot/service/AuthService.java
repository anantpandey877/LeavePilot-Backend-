package com.leavepilot.service;

import org.mindrot.jbcrypt.BCrypt;

import com.leavepilot.dao.UserDAO;
import com.leavepilot.dto.RegisterRequest;
import com.leavepilot.model.User;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        userDAO = new UserDAO();
    }

    public boolean registerUser(RegisterRequest request) {

    User existingUser = userDAO.getUserByEmail(request.getEmail());

    if (existingUser != null) {
        return false;
    }

    String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

    User user = new User();

    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());
    user.setPasswordHash(hashedPassword);
    user.setRole(request.getRole());
    user.setDepartment(request.getDepartment());
    user.setStatus("PENDING");

    return userDAO.registerUser(user);
}

    public User login(String email, String password) {

        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            return null;
        }

        if (!"APPROVED".equals(user.getStatus())) {
            return null;
        }

        boolean isPasswordValid;

        try {
            isPasswordValid = BCrypt.checkpw(password, user.getPasswordHash());
        } catch (IllegalArgumentException exception) {
            // Treat old or malformed password values as invalid credentials.
            return null;
        }

        if (!isPasswordValid) {
            return null;
        }

        return user;
    }
}