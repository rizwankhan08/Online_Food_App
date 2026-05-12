package com.ofa.servlet;

import com.model.User;
import com.model.Favorite;
import com.daoimpl.FavoriteDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/favorites")
public class FavoriteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        FavoriteDAOImpl favDAO = new FavoriteDAOImpl();

        List<Favorite> favRestaurants = favDAO.getFavoriteRestaurants(user.getId());
        List<Favorite> favMenuItems = favDAO.getFavoriteMenuItems(user.getId());

        req.setAttribute("favRestaurants", favRestaurants);
        req.setAttribute("favMenuItems", favMenuItems);
        req.getRequestDispatcher("/favorites.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        FavoriteDAOImpl favDAO = new FavoriteDAOImpl();

        String action = req.getParameter("action");
        String restaurantIdParam = req.getParameter("restaurantId");
        String menuIdParam = req.getParameter("menuId");
        String redirect = req.getParameter("redirect");

        if ("toggle".equals(action)) {
            if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
                int rid = Integer.parseInt(restaurantIdParam);
                if (favDAO.isRestaurantFavorited(user.getId(), rid)) {
                    favDAO.removeFavoriteRestaurant(user.getId(), rid);
                } else {
                    favDAO.addFavoriteRestaurant(user.getId(), rid);
                }
            }
            if (menuIdParam != null && !menuIdParam.isEmpty()) {
                int mid = Integer.parseInt(menuIdParam);
                if (favDAO.isMenuFavorited(user.getId(), mid)) {
                    favDAO.removeFavoriteMenu(user.getId(), mid);
                } else {
                    favDAO.addFavoriteMenu(user.getId(), mid);
                }
            }
        }

        if (redirect != null && !redirect.isEmpty()) {
            resp.sendRedirect(redirect);
        } else {
            resp.sendRedirect(req.getContextPath() + "/favorites");
        }
    }
}
