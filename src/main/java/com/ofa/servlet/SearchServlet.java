package com.ofa.servlet;

import com.model.*;
import com.daoimpl.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        String type = req.getParameter("type"); // "restaurant" or "menu"

        if (query == null || query.trim().isEmpty()) {
            req.getRequestDispatcher("/search.jsp").forward(req, resp);
            return;
        }

        query = query.trim();
        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        MenuDAOImpl menuDAO = new MenuDAOImpl();

        if ("menu".equals(type)) {
            List<Menu> menuResults = menuDAO.searchMenu(query);
            req.setAttribute("menuResults", menuResults);
        } else if ("restaurant".equals(type)) {
            List<Restaurant> restaurantResults = restaurantDAO.searchRestaurants(query);
            req.setAttribute("restaurantResults", restaurantResults);
        } else {
            // Search both
            List<Restaurant> restaurantResults = restaurantDAO.searchRestaurants(query);
            List<Menu> menuResults = menuDAO.searchMenu(query);
            req.setAttribute("restaurantResults", restaurantResults);
            req.setAttribute("menuResults", menuResults);
        }

        req.setAttribute("searchQuery", query);
        req.setAttribute("searchType", type);
        req.getRequestDispatcher("/search.jsp").forward(req, resp);
    }
}
