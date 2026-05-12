package com.ofa.servlet;

import com.model.User;
import com.model.Review;
import com.daoimpl.ReviewDAOImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        String restaurantIdParam = req.getParameter("restaurantId");
        String menuIdParam = req.getParameter("menuId");
        String ratingParam = req.getParameter("rating");
        String comment = req.getParameter("comment");

        if (ratingParam == null) {
            resp.sendRedirect(req.getContextPath() + "/restaurants");
            return;
        }

        Review review = new Review();
        review.setUserId(user.getId());
        review.setRating(Integer.parseInt(ratingParam));
        review.setComment(comment != null ? comment.trim() : "");

        if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
            review.setRestaurantId(Integer.parseInt(restaurantIdParam));
        }
        if (menuIdParam != null && !menuIdParam.isEmpty()) {
            review.setMenuId(Integer.parseInt(menuIdParam));
        }

        ReviewDAOImpl reviewDAO = new ReviewDAOImpl();
        reviewDAO.addReview(review);

        if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/menu?restaurantId=" + restaurantIdParam);
        } else {
            resp.sendRedirect(req.getContextPath() + "/restaurants");
        }
    }
}
