package com.leavepilot.servlet;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.leavepilot.model.User;
import com.leavepilot.service.UserService;
import com.leavepilot.util.GsonProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/api/admin/*")
public class AdminServlet extends HttpServlet {

    private UserService userService;
    private Gson gson;

    @Override
    public void init() {
        userService = new UserService();
        gson = GsonProvider.create();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/pending-users".equals(path)) {

            List<User> users = userService.getPendingUsers();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(
                    gson.toJson(users)
            );

        } else if ("/users".equals(path)) {

            List<User> users = userService.getAllUsers();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(
                    gson.toJson(users)
            );

        } else {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND
            );
        }
    }

    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if (path.matches("/users/\\d+/approve")) {

            approveUser(path, response);

        } else if (path.matches("/users/\\d+/reject")) {

            rejectUser(path, response);

        } else {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND
            );
        }
    }

    private void approveUser(String path,
                             HttpServletResponse response)
            throws IOException {

        String[] parts = path.split("/");

        int userId =
                Integer.parseInt(parts[2]);

        boolean success =
                userService.approveUser(userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {

            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().write(
                    """
                    {
                        "success": true,
                        "message": "User approved successfully"
                    }
                    """
            );

        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                    """
                    {
                        "success": false,
                        "message": "User approval failed"
                    }
                    """
            );
        }
    }

    private void rejectUser(String path,
                            HttpServletResponse response)
            throws IOException {

        String[] parts = path.split("/");

        int userId =
                Integer.parseInt(parts[2]);

        boolean success =
                userService.rejectUser(userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (success) {

            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().write(
                    """
                    {
                        "success": true,
                        "message": "User rejected successfully"
                    }
                    """
            );

        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                    """
                    {
                        "success": false,
                        "message": "User rejection failed"
                    }
                    """
            );
        }
    }
}
