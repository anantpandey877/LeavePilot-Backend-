package com.leavepilot.service;

import java.util.List;

import com.leavepilot.dao.LeaveBalanceDAO;
import com.leavepilot.dao.UserDAO;
import com.leavepilot.model.User;

public class UserService {

    private UserDAO userDAO;
    private LeaveBalanceDAO leaveBalanceDAO;

    public UserService() {
        userDAO = new UserDAO();
        leaveBalanceDAO = new LeaveBalanceDAO();
    }

    public boolean approveUser(int userId) {

        User user = userDAO.getUserById(userId);

        if (user == null) {
            return false;
        }

        boolean approved = userDAO.approveUser(userId);

        if (approved && "EMPLOYEE".equals(user.getRole())) {
            leaveBalanceDAO.createLeaveBalance(userId);
        }

        return approved;
    }

    public boolean rejectUser(int userId) {
        return userDAO.rejectUser(userId);
    }

    public List<User> getPendingUsers() {
        return userDAO.getPendingUsers();
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    public List<User> getUsersByDepartment(String department) {
        return userDAO.getUsersByDepartment(department);
    }
}