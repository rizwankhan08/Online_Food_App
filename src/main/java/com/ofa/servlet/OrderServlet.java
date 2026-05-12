package com.ofa.servlet;

import com.model.*;
import com.daoimpl.OrderDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String orderIdParam = req.getParameter("id");
        OrderDAOImpl orderDAO = new OrderDAOImpl();

        if (orderIdParam != null) {
            // Single order detail view
            try {
                int orderId = Integer.parseInt(orderIdParam);
                Order order = orderDAO.getOrderById(orderId);
                if (order != null && (order.getUserId() == user.getId() || user.isAdmin())) {
                    req.setAttribute("order", order);
                    req.getRequestDispatcher("/order-detail.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/orders");
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/orders");
            }
        } else {
            // Order history list
            List<Order> orders = orderDAO.getOrdersByUserId(user.getId());
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/orders.jsp").forward(req, resp);
        }
    }
}
