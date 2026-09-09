package com.leavepilot.dao;

import com.leavepilot.model.LeaveRequest;
import com.leavepilot.util.DatabaseConnection;
import com.leavepilot.dto.ManagerLeaveResponseDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDAO {

    public boolean applyLeave(LeaveRequest leaveRequest) {

        String sql = "INSERT INTO leave_requests (user_id, leave_type, start_date, end_date, reason) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, leaveRequest.getUserId());
            pstmt.setString(2, leaveRequest.getLeaveType());
            pstmt.setDate(3, java.sql.Date.valueOf(leaveRequest.getStartDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(leaveRequest.getEndDate()));
            pstmt.setString(5, leaveRequest.getReason());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public LeaveRequest getLeaveRequestById(int leaveId) {

        String sql = "SELECT * FROM leave_requests WHERE id = ?";
        LeaveRequest leaveRequest = null;

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, leaveId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                leaveRequest = mapLeaveRequest(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return leaveRequest;
    }

    public List<LeaveRequest> getLeavesByUserId(int userId) {

        String sql = "SELECT * FROM leave_requests WHERE user_id = ?";
        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                leaveRequests.add(mapLeaveRequest(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return leaveRequests;
    }

    public List<ManagerLeaveResponseDTO> getLeavesByDepartment(String department) {

    String sql = """
        SELECT
            lr.id,
            lr.user_id,
            u.full_name,
            u.email,
            u.department,
            u.role,
            lr.leave_type,
            lr.start_date,
            lr.end_date,
            lr.reason,
            lr.status,
            lr.applied_at
        FROM leave_requests lr
        JOIN users u
        ON lr.user_id = u.id
        WHERE u.department = ?
        ORDER BY lr.applied_at DESC
        """;

    List<ManagerLeaveResponseDTO> leaves = new ArrayList<>();

    try {

        Connection conn = DatabaseConnection.getConnection();

        PreparedStatement pstmt =
                conn.prepareStatement(sql);

        pstmt.setString(1, department);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {

            leaves.add(
                new ManagerLeaveResponseDTO(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getString("role"),
                    rs.getString("leave_type"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate(),
                    rs.getString("reason"),
                    rs.getString("status"),
                    rs.getTimestamp("applied_at").toLocalDateTime()
                )
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return leaves;
}
    public boolean approveLeave(int leaveId, int managerId) {

        String sql = "UPDATE leave_requests SET status = 'APPROVED', reviewed_by = ?, reviewed_at = NOW() WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, managerId);
            pstmt.setInt(2, leaveId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean rejectLeave(int leaveId, int managerId) {

        String sql = "UPDATE leave_requests SET status = 'REJECTED', reviewed_by = ?, reviewed_at = NOW() WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, managerId);
            pstmt.setInt(2, leaveId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean cancelLeave(int leaveId) {

        String sql = "UPDATE leave_requests SET status = 'CANCELLED' WHERE id = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, leaveId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private LeaveRequest mapLeaveRequest(ResultSet rs) throws Exception {

        return new LeaveRequest(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getString("leave_type"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getString("reason"),
                rs.getString("status"),
                (Integer) rs.getObject("reviewed_by"),
                rs.getTimestamp("applied_at").toLocalDateTime(),
                rs.getTimestamp("reviewed_at") != null
                        ? rs.getTimestamp("reviewed_at").toLocalDateTime()
                        : null);
    }
}