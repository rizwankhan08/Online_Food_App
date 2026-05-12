package com.ofa.servlet;

import com.model.User;
import com.daoimpl.UserDAOImpl;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action");
        UserDAOImpl userDAO = new UserDAOImpl();

        if ("updateProfile".equals(action)) {
            user.setUsername(req.getParameter("username"));
            user.setEmail(req.getParameter("email"));
            user.setPhone(req.getParameter("phone"));
            user.setAddress(req.getParameter("address"));

            int result = userDAO.updateUser(user);
            if (result > 0) {
                session.setAttribute("user", user);
                req.setAttribute("success", "Profile updated successfully!");
            } else {
                req.setAttribute("error", "Failed to update profile.");
            }
        } else if ("changePassword".equals(action)) {
            String currentPassword = req.getParameter("currentPassword");
            String newPassword = req.getParameter("newPassword");
            String confirmPassword = req.getParameter("confirmPassword");

            if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
                req.setAttribute("error", "Current password is incorrect.");
            } else if (newPassword == null || newPassword.length() < 6) {
                req.setAttribute("error", "New password must be at least 6 characters.");
            } else if (!newPassword.equals(confirmPassword)) {
                req.setAttribute("error", "Passwords do not match.");
            } else {
                String hashed = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
                int result = userDAO.updatePassword(user.getId(), hashed);
                if (result > 0) {
                    user.setPassword(hashed);
                    session.setAttribute("user", user);
                    req.setAttribute("success", "Password changed successfully!");
                } else {
                    req.setAttribute("error", "Failed to change password.");
                }
            }
        }
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}
