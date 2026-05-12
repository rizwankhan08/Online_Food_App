package com.ofa.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.daoimpl.MenuDAOImpl;
import com.daoimpl.RestaurantDAOImpl;
import com.daoimpl.ReviewDAOImpl;
import com.model.Menu;
import com.model.Restaurant;
import com.model.Review;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ridParam = req.getParameter("restaurantId");
        if (ridParam == null || ridParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/restaurants");
            return;
        }

        try {
            int restaurantId = Integer.parseInt(ridParam);
            MenuDAOImpl menuDAO = new MenuDAOImpl();
            RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
            ReviewDAOImpl reviewDAO = new ReviewDAOImpl();

            Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
            List<Menu> menuList = menuDAO.getMenuByRestaurantId(restaurantId);
            List<Review> reviews = reviewDAO.getReviewsByRestaurantId(restaurantId);
            List<String> categories = menuDAO.getAllCategories();

            req.setAttribute("restaurant", restaurant);
            req.setAttribute("menuList", menuList);
            req.setAttribute("reviews", reviews);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/menu.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/restaurants");
        }
    }
}