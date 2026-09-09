package com.leavepilot.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ManagerLeaveResponseDTO {

    private int leaveId;
    private int userId;
    private String fullName;
    private String email;
    private String department;
    private String role;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status;
    private LocalDateTime appliedAt;

    public ManagerLeaveResponseDTO(
            int leaveId,
            int userId,
            String fullName,
            String email,
            String department,
            String role,
            String leaveType,
            LocalDate startDate,
            LocalDate endDate,
            String reason,
            String status,
            LocalDateTime appliedAt) {

        this.leaveId = leaveId;
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.role = role;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public int getLeaveId() { return leaveId; }
    public int getUserId() { return userId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
    public String getRole() { return role; }
    public String getLeaveType() { return leaveType; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getReason() { return reason; }
    public String getStatus() { return status; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
}