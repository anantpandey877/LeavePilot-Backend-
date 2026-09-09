package com.leavepilot.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import com.leavepilot.util.DatabaseConnection;
import com.leavepilot.model.User;


public class UserDAO {
 
   public boolean registerUser(User user) {

    String sql =  "INSERT INTO users (full_name, email, password_hash, role, department, status) VALUES (?, ?, ?, ?, ?, ?)";

    try {
        Connection conn = DatabaseConnection.getConnection();

        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, user.getFullName());
        pstmt.setString(2, user.getEmail());
        pstmt.setString(3, user.getPasswordHash());
        pstmt.setString(4, user.getRole());
        pstmt.setString(5, user.getDepartment());
        pstmt.setString(6, user.getStatus());

        int rowsAffected = pstmt.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

    public User getUserByEmail(String email) {
        Connection conn = null;
        String sql = "SELECT * FROM users WHERE email = ?";
        User user = null;

        try {
            conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("role"),
                    rs.getString("department"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;  
    }

public List<User> getAllUsers() {
    Connection conn = null;
    String sql = "SELECT * FROM users";
    List<User> users = new java.util.ArrayList<>();

    try {
        conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        var rs = pstmt.executeQuery();

        while (rs.next()) {
            User user = new User(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("role"),
                rs.getString("department"),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime()
            );
            users.add(user);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return users;
}

public List<User> getPendingUsers() {
    Connection conn = null;
    String sql = "SELECT * FROM users WHERE status = 'PENDING'";
    List<User> users = new ArrayList<>();

    try {
        conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        var rs = pstmt.executeQuery();

        while (rs.next()) {
            User user = new User(
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("role"),
                rs.getString("department"),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime()
            );
            users.add(user);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return users;
}
  public boolean approveUser(int userId) {
        Connection conn = null;
        String sql = "UPDATE users SET status = 'APPROVED' WHERE id = ?";

        try {
            conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was updated
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

public boolean rejectUser(int userId) {
        Connection conn = null;
        String sql = "UPDATE users SET status = 'REJECTED' WHERE id = ?";

        try {
            conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was updated
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserById(int userId) {
        Connection conn = null;
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;

        try {
            conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("password_hash"),
                    rs.getString("role"),
                    rs.getString("department"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            }

        return user;  
    }

    public List<User> getUsersByDepartment(String department){
        Connection conn = null;
        String sql = "select * from users where department = ? ";
        List<User> users = new ArrayList<>();

        try{
            conn = DatabaseConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, department);

            var rs = pstmt.executeQuery();

            while(rs.next()){
                User user = new User( 
                rs.getInt("id"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                rs.getString("role"),
                rs.getString("department"),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime());

                users.add(user);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return users;
    }
}