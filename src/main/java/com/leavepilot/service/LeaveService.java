package com.leavepilot.service;

import java.time.temporal.ChronoUnit;

import com.leavepilot.dao.LeaveBalanceDAO;
import com.leavepilot.dao.LeaveRequestDAO;
import com.leavepilot.model.LeaveBalance;
import com.leavepilot.model.LeaveRequest;

public class LeaveService {

    private LeaveRequestDAO leaveRequestDAO;
    private LeaveBalanceDAO leaveBalanceDAO;

    public LeaveService() {
        leaveRequestDAO = new LeaveRequestDAO();
        leaveBalanceDAO = new LeaveBalanceDAO();
    }

    public boolean applyLeave(LeaveRequest leaveRequest) {

        LeaveBalance balance = leaveBalanceDAO
                .getLeaveBalanceByUserId(leaveRequest.getUserId());

        long leaveDays = ChronoUnit.DAYS.between(
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate()) + 1;

        switch (leaveRequest.getLeaveType()) {

            case "CASUAL":
                if (balance.getCasualLeave() < leaveDays)
                    return false;
                break;

            case "SICK":
                if (balance.getSickLeave() < leaveDays)
                    return false;
                break;

            case "EARNED":
                if (balance.getEarnedLeave() < leaveDays)
                    return false;
                break;

            default:
                return false;
        }

        return leaveRequestDAO.applyLeave(leaveRequest);
    }

    public boolean approveLeave(int leaveId, int managerId) {

        LeaveRequest leaveRequest = leaveRequestDAO.getLeaveRequestById(leaveId);

        if (leaveRequest == null) {
            return false;
        }

        boolean approved = leaveRequestDAO.approveLeave(leaveId, managerId);

        if (!approved) {
            return false;
        }

        LeaveBalance balance = leaveBalanceDAO.getLeaveBalanceByUserId(
                leaveRequest.getUserId());

        int leaveDays = (int) (ChronoUnit.DAYS.between(
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate()) + 1);

        switch (leaveRequest.getLeaveType()) {

            case "CASUAL":
                balance.setCasualLeave(
                        balance.getCasualLeave() - leaveDays);
                break;

            case "SICK":
                balance.setSickLeave(
                        balance.getSickLeave() - leaveDays);
                break;

            case "EARNED":
                balance.setEarnedLeave(
                        balance.getEarnedLeave() - leaveDays);
                break;
        }

        leaveBalanceDAO.updateLeaveBalance(balance);

        return true;
    }

    public boolean rejectLeave(int leaveId, int managerId) {
        return leaveRequestDAO.rejectLeave(leaveId, managerId);
    }

    public boolean cancelLeave(int leaveId) {
        return leaveRequestDAO.cancelLeave(leaveId);
    }
}