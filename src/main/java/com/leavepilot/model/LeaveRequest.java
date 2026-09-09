package com.leavepilot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LeaveRequest {

    private int id;
    private int userId;
    private String leaveType;

    private LocalDate startDate;
    private LocalDate endDate;

    private String reason;
    private String status;

    private Integer reviewedBy;

    private LocalDateTime appliedAt;
    private LocalDateTime reviewedAt;

        // Constructors

    public LeaveRequest() {}

    public LeaveRequest(int id, int userId, String leaveType, LocalDate startDate, LocalDate endDate, String reason, String status, Integer reviewedBy, LocalDateTime appliedAt, LocalDateTime reviewedAt) {
        this.id = id;
        this.userId = userId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
        this.reviewedBy = reviewedBy;
        this.appliedAt = appliedAt;
        this.reviewedAt = reviewedAt;
    }

    // getters and setters
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Integer reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
}

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}   