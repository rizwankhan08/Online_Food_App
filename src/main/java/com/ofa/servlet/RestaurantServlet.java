package com.ofa.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.daoimpl.RestaurantDAOImpl;
import com.model.Restaurant;

@WebServlet("/restaurants")
public class RestaurantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();
        String search = req.getParameter("search");
        String cuisine = req.getParameter("cuisine");

        List<Restaurant> restaurants;
        if (search != null && !search.trim().isEmpty()) {
            restaurants = restaurantDAO.searchRestaurants(search.trim());
            req.setAttribute("searchQuery", search.trim());
        } else if (cuisine != null && !cuisine.trim().isEmpty()) {
            restaurants = restaurantDAO.getRestaurantsByCuisine(cuisine.trim());
            req.setAttribute("selectedCuisine", cuisine.trim());
        } else {
            restaurants = restaurantDAO.getAllRestaurants();
        }

        List<String> cuisineTypes = restaurantDAO.getAllCuisineTypes();

        req.setAttribute("restaurants", restaurants);
        req.setAttribute("cuisineTypes", cuisineTypes);
        req.getRequestDispatcher("/restaurants.jsp").forward(req, resp);
    }
}