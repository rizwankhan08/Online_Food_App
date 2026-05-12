package com.ofa.servlet;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import com.daoimpl.UserDAOImpl;
import com.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String role = req.getParameter("role");

        // Backend validation
        if (username == null || username.trim().isEmpty() || email == null || email.trim().isEmpty()
                || password == null || password.length() < 6) {
            req.setAttribute("error", "Please fill all fields. Password must be at least 6 characters.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        UserDAOImpl userDAO = new UserDAOImpl();

        // Check if username already exists
        if (userDAO.getUserByUsername(username.trim()) != null) {
            req.setAttribute("error", "Username already exists. Please choose another.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        // Check if email already exists
        if (userDAO.getUserByEmail(email.trim()) != null) {
            req.setAttribute("error", "Email already registered. Please use another.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        User user = new User(username.trim(), hashedPassword, email.trim(),
                address != null ? address.trim() : "", role != null ? role : "customer");
        user.setPhone(phone != null ? phone.trim() : "");

        int result = userDAO.addUser(user);

        if (result == 1) {
            req.setAttribute("success", "Registration successful! Please login.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Registration failed. Please try again.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
