package com.leavepilot.dto;

public class RegisterRequest {

    private String fullName;
    private String email;
    private String password;
    private String role;
    private String department;

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }
}