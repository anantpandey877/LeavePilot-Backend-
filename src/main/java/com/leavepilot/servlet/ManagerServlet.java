package com.leavepilot.servlet;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.leavepilot.dao.LeaveRequestDAO;
import com.leavepilot.dto.ApiResponse;
import com.leavepilot.dto.ManagerLeaveResponseDTO;
import com.leavepilot.service.LeaveService;
import com.leavepilot.util.GsonProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/manager/*")
public class ManagerServlet extends HttpServlet {

    private LeaveService leaveService;
    private LeaveRequestDAO leaveRequestDAO;
    private Gson gson;

    @Override
    public void init() {
        leaveService = new LeaveService();
        leaveRequestDAO = new LeaveRequestDAO();
        gson = GsonProvider.create();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/leaves".equals(path)) {

            String department = request.getParameter("department");

            List<ManagerLeaveResponseDTO> leaves = leaveRequestDAO.getLeavesByDepartment(department);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().write(
                    gson.toJson(leaves));

        } else {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path.matches("/leaves/\\d+/approve")) {

            approveLeave(path, request, response);

        } else if (path.matches("/leaves/\\d+/reject")) {

            rejectLeave(path, request, response);

        } else {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void approveLeave(String path,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int leaveId = Integer.parseInt(
                path.split("/")[2]);

        int managerId = Integer.parseInt(
                request.getParameter("managerId"));

        boolean success = leaveService.approveLeave(
                leaveId,
                managerId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {

            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().write(
                    gson.toJson(
                            new ApiResponse(
                                    true,
                                    "Leave approved successfully")));

        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                    gson.toJson(
                            new ApiResponse(
                                    false,
                                    "Leave approval failed")));
        }
    }

    private void rejectLeave(String path,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        int leaveId = Integer.parseInt(
                path.split("/")[2]);

        int managerId = Integer.parseInt(
                request.getParameter("managerId"));

        boolean success = leaveService.rejectLeave(
                leaveId,
                managerId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {

            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().write(
                    gson.toJson(
                            new ApiResponse(
                                    true,
                                    "Leave rejected successfully")));

        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                    gson.toJson(
                            new ApiResponse(
                                    false,
                                    "Leave rejection failed")));
        }
    }
}