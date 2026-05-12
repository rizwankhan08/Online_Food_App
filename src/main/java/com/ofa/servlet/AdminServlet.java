package com.ofa.servlet;

import com.model.*;
import com.daoimpl.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) pathInfo = "/dashboard";

        switch (pathInfo) {
            case "/dashboard" -> showDashboard(req, resp);
            case "/restaurants" -> showRestaurants(req, resp);
            case "/menu" -> showMenu(req, resp);
            case "/orders" -> showOrders(req, resp);
            case "/users" -> showUsers(req, resp);
            default -> showDashboard(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) { resp.sendRedirect(req.getContextPath() + "/admin/dashboard"); return; }

        switch (pathInfo) {
            case "/restaurant/add" -> addRestaurant(req, resp);
            case "/restaurant/update" -> updateRestaurant(req, resp);
            case "/restaurant/delete" -> deleteRestaurant(req, resp);
            case "/menu/add" -> addMenuItem(req, resp);
            case "/menu/update" -> updateMenuItem(req, resp);
            case "/menu/delete" -> deleteMenuItem(req, resp);
            case "/order/status" -> updateOrderStatus(req, resp);
            default -> resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        }
    }

    private void showDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        MenuDAOImpl menuDAO = new MenuDAOImpl();

        req.setAttribute("totalUsers", userDAO.countUsers());
        req.setAttribute("totalRestaurants", restaurantDAO.countRestaurants());
        req.setAttribute("totalOrders", orderDAO.countOrders());
        req.setAttribute("totalRevenue", orderDAO.getTotalRevenue());
        req.setAttribute("totalMenuItems", menuDAO.countMenuItems());
        req.setAttribute("recentOrders", orderDAO.getRecentOrders(10));
        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }

    private void showRestaurants(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        req.setAttribute("restaurants", restaurantDAO.getAllRestaurants());
        req.getRequestDispatcher("/admin/restaurants.jsp").forward(req, resp);
    }

    private void showMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MenuDAOImpl menuDAO = new MenuDAOImpl();
        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        String rid = req.getParameter("restaurantId");
        if (rid != null) {
            req.setAttribute("menuList", menuDAO.getMenuByRestaurantId(Integer.parseInt(rid)));
            req.setAttribute("selectedRestaurant", restaurantDAO.getRestaurantById(Integer.parseInt(rid)));
        }
        req.setAttribute("restaurants", restaurantDAO.getAllRestaurants());
        req.getRequestDispatcher("/admin/menu.jsp").forward(req, resp);
    }

    private void showOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        String status = req.getParameter("status");
        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderDAO.getOrdersByStatus(status);
        } else {
            orders = orderDAO.getAllOrders();
        }
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
    }

    private void showUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAOImpl userDAO = new UserDAOImpl();
        req.setAttribute("users", userDAO.getAllUsers());
        req.getRequestDispatcher("/admin/users.jsp").forward(req, resp);
    }

    private void addRestaurant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Restaurant r = new Restaurant();
        r.setName(req.getParameter("name")); r.setDescription(req.getParameter("description"));
        r.setLocation(req.getParameter("location")); r.setCuisineType(req.getParameter("cuisineType"));
        r.setRating(Double.parseDouble(req.getParameter("rating")));
        r.setDeliveryTime(req.getParameter("deliveryTime"));
        r.setMinOrder(Double.parseDouble(req.getParameter("minOrder")));
        r.setImageUrl(req.getParameter("imageUrl"));
        new RestaurantDAOImpl().addRestaurant(r);
        resp.sendRedirect(req.getContextPath() + "/admin/restaurants");
    }

    private void updateRestaurant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Restaurant r = new Restaurant();
        r.setId(Integer.parseInt(req.getParameter("id")));
        r.setName(req.getParameter("name")); r.setDescription(req.getParameter("description"));
        r.setLocation(req.getParameter("location")); r.setCuisineType(req.getParameter("cuisineType"));
        r.setRating(Double.parseDouble(req.getParameter("rating")));
        r.setDeliveryTime(req.getParameter("deliveryTime"));
        r.setMinOrder(Double.parseDouble(req.getParameter("minOrder")));
        r.setImageUrl(req.getParameter("imageUrl"));
        r.setActive("on".equals(req.getParameter("isActive")) || "true".equals(req.getParameter("isActive")));
        new RestaurantDAOImpl().updateRestaurant(r);
        resp.sendRedirect(req.getContextPath() + "/admin/restaurants");
    }

    private void deleteRestaurant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        new RestaurantDAOImpl().deleteRestaurant(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/admin/restaurants");
    }

    private void addMenuItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Menu m = new Menu();
        m.setRestaurantId(Integer.parseInt(req.getParameter("restaurantId")));
        m.setName(req.getParameter("name")); m.setDescription(req.getParameter("description"));
        m.setPrice(Double.parseDouble(req.getParameter("price")));
        m.setCategory(req.getParameter("category"));
        m.setRating(Double.parseDouble(req.getParameter("rating")));
        m.setVeg("on".equals(req.getParameter("isVeg")));
        m.setImageUrl(req.getParameter("imageUrl"));
        new MenuDAOImpl().addMenu(m);
        resp.sendRedirect(req.getContextPath() + "/admin/menu?restaurantId=" + m.getRestaurantId());
    }

    private void updateMenuItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Menu m = new Menu();
        m.setId(Integer.parseInt(req.getParameter("id")));
        m.setRestaurantId(Integer.parseInt(req.getParameter("restaurantId")));
        m.setName(req.getParameter("name")); m.setDescription(req.getParameter("description"));
        m.setPrice(Double.parseDouble(req.getParameter("price")));
        m.setCategory(req.getParameter("category"));
        m.setRating(Double.parseDouble(req.getParameter("rating")));
        m.setVeg("on".equals(req.getParameter("isVeg")));
        m.setAvailable("on".equals(req.getParameter("isAvailable")));
        m.setImageUrl(req.getParameter("imageUrl"));
        new MenuDAOImpl().updateMenu(m);
        resp.sendRedirect(req.getContextPath() + "/admin/menu?restaurantId=" + m.getRestaurantId());
    }

    private void deleteMenuItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("restaurantId");
        new MenuDAOImpl().deleteMenu(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/admin/menu" + (rid != null ? "?restaurantId=" + rid : ""));
    }

    private void updateOrderStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String status = req.getParameter("status");
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        orderDAO.updateOrderStatus(orderId, status);
        if ("delivered".equals(status)) orderDAO.updatePaymentStatus(orderId, "paid");
        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }
}
