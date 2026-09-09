package com.leavepilot.servlet;

import java.io.IOException;

import com.google.gson.Gson;
import com.leavepilot.dto.LoginRequest;
import com.leavepilot.dto.RegisterRequest;
import com.leavepilot.model.User;
import com.leavepilot.service.AuthService;
import com.leavepilot.util.GsonProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/api/auth/*")
public class AuthServlet extends HttpServlet {

    private AuthService authService;
    private Gson gson;

    @Override
    public void init() {
        authService = new AuthService();
        gson = GsonProvider.create();
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.getWriter().write("Auth Servlet Working");
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();

        if ("/register".equals(path)) {

            register(request, response);

        } else if ("/login".equals(path)) {

            login(request, response);

        } else {

            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private void register(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        RegisterRequest registerRequest = gson.fromJson(request.getReader(), RegisterRequest.class);

        boolean registered = authService.registerUser(registerRequest);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (registered) {

            response.setStatus(HttpServletResponse.SC_CREATED);

            response.getWriter().write(
                    """
                            {
                              "success": true,
                              "message": "Registration submitted successfully. Awaiting admin approval."
                            }
                            """);

        } else {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            response.getWriter().write(
                    """
                            {
                              "success": false,
                              "message": "Email already exists."
                            }
                            """);
        }
    }

    private void login(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        LoginRequest loginRequest = gson.fromJson(
                request.getReader(),
                LoginRequest.class);

        User user = authService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (user != null) {

            response.setStatus(HttpServletResponse.SC_OK);

            // Never send the stored password hash to the browser.
            user.setPasswordHash(null);

            response.getWriter().write(
                    gson.toJson(user));

        } else {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().write(
                    """
                            {
                                "success": false,
                                "message": "Invalid credentials or account not approved."
                            }
                            """);
        }
    }
}