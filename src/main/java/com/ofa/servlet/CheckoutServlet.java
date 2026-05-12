package com.ofa.servlet;

import com.model.*;
import com.daoimpl.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cart == null || cart.getItems().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart.jsp");
            return;
        }

        AddressDAOImpl addressDAO = new AddressDAOImpl();
        List<Address> addresses = addressDAO.getAddressesByUserId(user.getId());
        req.setAttribute("addresses", addresses);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cart == null || cart.getItems().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart.jsp");
            return;
        }

        String deliveryAddress = req.getParameter("deliveryAddress");
        String paymentMethod = req.getParameter("paymentMethod");
        String specialInstructions = req.getParameter("specialInstructions");

        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            deliveryAddress = user.getAddress();
        }

        double totalAmount = cart.getTotalAmount();
        double deliveryFee = 30.00;
        double taxAmount = Math.round(totalAmount * 0.05 * 100.0) / 100.0; // 5% GST
        double grandTotal = totalAmount + deliveryFee + taxAmount;

        // Determine restaurant ID from first cart item
        int restaurantId = 0;
        MenuDAOImpl menuDAO = new MenuDAOImpl();
        CartItem firstItem = cart.getItems().values().iterator().next();
        Menu firstMenu = menuDAO.getMenuById(firstItem.getId());
        if (firstMenu != null) restaurantId = firstMenu.getRestaurantId();

        Order order = new Order();
        order.setUserId(user.getId());
        order.setRestaurantId(restaurantId);
        order.setTotalAmount(totalAmount);
        order.setDeliveryFee(deliveryFee);
        order.setTaxAmount(taxAmount);
        order.setGrandTotal(grandTotal);
        order.setDeliveryAddress(deliveryAddress);
        order.setPaymentMethod(paymentMethod != null ? paymentMethod : "cod");
        order.setPaymentStatus("cod".equals(paymentMethod) ? "pending" : "paid");
        order.setSpecialInstructions(specialInstructions);
        order.setEstimatedDelivery("30-45 min");

        // Create order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems().values()) {
            orderItems.add(new OrderItem(cartItem.getId(), cartItem.getName(),
                    cartItem.getQuantity(), cartItem.getPrice()));
        }

        OrderDAOImpl orderDAO = new OrderDAOImpl();
        int orderId = orderDAO.placeOrder(order);

        if (orderId > 0) {
            orderDAO.addOrderItems(orderId, orderItems);
            session.removeAttribute("cart"); // Clear cart

            Order placedOrder = orderDAO.getOrderById(orderId);
            req.setAttribute("order", placedOrder);
            req.getRequestDispatcher("/order-confirmation.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Failed to place order. Please try again.");
            req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
        }
    }
}
