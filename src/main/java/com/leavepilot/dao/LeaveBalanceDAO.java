package com.leavepilot.dao;

import com.leavepilot.model.LeaveBalance;
import com.leavepilot.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LeaveBalanceDAO {

    public boolean createLeaveBalance(int userId) {

        String sql = """
                INSERT INTO leave_balances
                (
                    user_id,
                    casual_leave,
                    sick_leave,
                    earned_leave
                )
                VALUES (?, 10, 8, 15)
                """;

        try {
            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql);

            pstmt.setInt(1, userId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public LeaveBalance getLeaveBalanceByUserId(int userId) {

        String sql =
                "SELECT * FROM leave_balances WHERE user_id = ?";

        LeaveBalance leaveBalance = null;

        try {
            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql);

            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                leaveBalance = new LeaveBalance(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("casual_leave"),
                        rs.getInt("sick_leave"),
                        rs.getInt("earned_leave")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return leaveBalance;
    }

    public boolean updateLeaveBalance(LeaveBalance balance) {

        String sql = """
                UPDATE leave_balances
                SET
                    casual_leave = ?,
                    sick_leave = ?,
                    earned_leave = ?
                WHERE user_id = ?
                """;

        try {

            Connection conn =
                    DatabaseConnection.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement(sql);

            pstmt.setInt(1,
                    balance.getCasualLeave());

            pstmt.setInt(2,
                    balance.getSickLeave());

            pstmt.setInt(3,
                    balance.getEarnedLeave());

            pstmt.setInt(4,
                    balance.getUserId());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}