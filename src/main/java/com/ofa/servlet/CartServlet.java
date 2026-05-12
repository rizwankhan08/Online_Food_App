package com.ofa.servlet;

import com.model.Cart;
import com.model.CartItem;
import com.daoimpl.MenuDAOImpl;
import com.model.Menu;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Handles cart operations: add, update quantity, remove items.
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        String menuIdParam = request.getParameter("menuId");
        String quantityParam = request.getParameter("quantity");

        if (menuIdParam == null || menuIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        int menuId;
        int quantity;
        try {
            menuId = Integer.parseInt(menuIdParam);
            quantity = (quantityParam != null && !quantityParam.isEmpty())
                    ? Integer.parseInt(quantityParam) : 1;
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        switch (action) {
            case "add" -> {
                MenuDAOImpl dao = new MenuDAOImpl();
                Menu menu = dao.getMenuById(menuId);
                if (menu != null) {
                    CartItem item = new CartItem(menu.getId(), menu.getName(), menu.getPrice(), quantity);
                    cart.addItem(item);
                }
            }
            case "update" -> cart.updateItem(menuId, quantity);
            case "delete" -> cart.removeItem(menuId);
        }

        session.setAttribute("cart", cart);
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}