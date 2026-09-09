package com.leavepilot.model;

public class LeaveBalance {

    private int id;
    private int userId;
    private int casualLeave;
    private int sickLeave;
    private int earnedLeave;

    public LeaveBalance() {}

    // Constructors
    public LeaveBalance(int id, int userId, int casualLeave, int sickLeave, int earnedLeave) {
        this.id = id;
        this.userId = userId;
        this.casualLeave = casualLeave;
        this.sickLeave = sickLeave;
        this.earnedLeave = earnedLeave;
    }

    // Getters
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

    public int getCasualLeave() {
        return casualLeave;
    }

    public void setCasualLeave(int casualLeave) {
        this.casualLeave = casualLeave;
    }

    public int getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(int sickLeave) {
        this.sickLeave = sickLeave;
    }

    public int getEarnedLeave() {
        return earnedLeave;
    }

    public void setEarnedLeave(int earnedLeave) {
        this.earnedLeave = earnedLeave;
    }
    
}