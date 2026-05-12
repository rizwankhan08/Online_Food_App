package com.dao;

import com.model.Order;
import com.model.OrderItem;
import java.util.List;

/**
 * Data Access Object interface for Order operations.
 */
public interface OrderDAO {

    /** Place a new order and return generated order ID */
    int placeOrder(Order order);

    /** Add items to an order */
    void addOrderItems(int orderId, List<OrderItem> items);

    /** Get order by ID */
    Order getOrderById(int orderId);

    /** Get all orders for a user */
    List<Order> getOrdersByUserId(int userId);

    /** Get all orders (admin) */
    List<Order> getAllOrders();

    /** Update order status */
    int updateOrderStatus(int orderId, String status);

    /** Update payment status */
    int updatePaymentStatus(int orderId, String status);

    /** Get order items by order ID */
    List<OrderItem> getOrderItems(int orderId);

    /** Count total orders */
    int countOrders();

    /** Get total revenue */
    double getTotalRevenue();

    /** Get recent orders (admin dashboard) */
    List<Order> getRecentOrders(int limit);

    /** Get orders by status */
    List<Order> getOrdersByStatus(String status);
}
