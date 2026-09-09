package com.leavepilot.servlet;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.leavepilot.dao.LeaveBalanceDAO;
import com.leavepilot.dao.LeaveRequestDAO;
import com.leavepilot.dto.ApiResponse;
import com.leavepilot.model.LeaveBalance;
import com.leavepilot.model.LeaveRequest;
import com.leavepilot.service.LeaveService;
import com.leavepilot.util.GsonProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/employee/*")
public class EmployeeServlet extends HttpServlet {

    private LeaveService leaveService;
    private LeaveBalanceDAO leaveBalanceDAO;
    private LeaveRequestDAO leaveRequestDAO;
    private Gson gson;

    @Override
    public void init() {

        leaveService = new LeaveService();
        leaveBalanceDAO = new LeaveBalanceDAO();
        leaveRequestDAO = new LeaveRequestDAO();
        gson = GsonProvider.create();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/balance".equals(path)) {

            getLeaveBalance(request, response);

        } else if ("/leaves".equals(path)) {

            getMyLeaves(request, response);

        } else {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/leaves".equals(path)) {

            applyLeave(request, response);

        } else {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path.matches("/leaves/\\d+/cancel")) {

            cancelLeave(path, response);

        } else {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getLeaveBalance(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int userId = Integer.parseInt(
                request.getParameter("userId"));

        LeaveBalance balance = leaveBalanceDAO
                .getLeaveBalanceByUserId(userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                gson.toJson(balance));
    }

    private void getMyLeaves(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int userId = Integer.parseInt(
                request.getParameter("userId"));

        List<LeaveRequest> leaves = leaveRequestDAO
                .getLeavesByUserId(userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                gson.toJson(leaves));
    }

    private void applyLeave(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        LeaveRequest leaveRequest = gson.fromJson(
                request.getReader(),
                LeaveRequest.class);

        boolean success = leaveService.applyLeave(
                leaveRequest);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {

            response.setStatus(HttpServletResponse.SC_CREATED);

            response.getWriter().write(
                    gson.toJson(
                            new ApiResponse(
                                    true,
                                    "Leave applied successfully")));

        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                    gson.toJson(
                            new ApiResponse(
                                    false,
                                    "Insufficient leave balance")));
        }
    }

    private void cancelLeave(String path,
            HttpServletResponse response)
            throws IOException {

        String[] parts = path.split("/");

        int leaveId = Integer.parseInt(parts[2]);

        boolean success = leaveService.cancelLeave(
                leaveId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {

            response.getWriter().write(
                    gson.toJson(
                            new ApiResponse(
                                    true,
                                    "Leave cancelled successfully")));

        } else {

            response.getWriter().write(
                    gson.toJson(
                            new ApiResponse(
                                    false,
                                    "Failed to cancel leave")));
        }
    }
}