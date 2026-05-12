package com.daoimpl;

import com.dao.OrderDAO;
import com.model.Order;
import com.model.OrderItem;
import com.util.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAOImpl implements OrderDAO {
    private static final Logger LOGGER = Logger.getLogger(OrderDAOImpl.class.getName());

    @Override
    public int placeOrder(Order order) {
        String sql = "INSERT INTO orders(user_id,restaurant_id,total_amount,delivery_fee,tax_amount,grand_total,delivery_address,payment_method,payment_status,order_status,special_instructions,estimated_delivery) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getUserId()); ps.setInt(2, order.getRestaurantId());
            ps.setDouble(3, order.getTotalAmount()); ps.setDouble(4, order.getDeliveryFee());
            ps.setDouble(5, order.getTaxAmount()); ps.setDouble(6, order.getGrandTotal());
            ps.setString(7, order.getDeliveryAddress()); ps.setString(8, order.getPaymentMethod());
            ps.setString(9, order.getPaymentStatus() != null ? order.getPaymentStatus() : "pending");
            ps.setString(10, "placed"); ps.setString(11, order.getSpecialInstructions());
            ps.setString(12, order.getEstimatedDelivery());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error placing order", e); }
        return -1;
    }

    @Override
    public void addOrderItems(int orderId, List<OrderItem> items) {
        String sql = "INSERT INTO order_items(order_id,menu_id,item_name,quantity,unit_price,total_price) VALUES(?,?,?,?,?,?)";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (OrderItem item : items) {
                ps.setInt(1, orderId); ps.setInt(2, item.getMenuId());
                ps.setString(3, item.getItemName()); ps.setInt(4, item.getQuantity());
                ps.setDouble(5, item.getUnitPrice()); ps.setDouble(6, item.getTotalPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error adding order items", e); }
    }

    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT o.*, u.username, r.name as restaurant_name FROM orders o LEFT JOIN users u ON o.user_id=u.id LEFT JOIN restaurant r ON o.restaurant_id=r.id WHERE o.id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { Order o = mapOrder(rs); o.setOrderItems(getOrderItems(orderId)); return o; }
            }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.username, r.name as restaurant_name FROM orders o LEFT JOIN users u ON o.user_id=u.id LEFT JOIN restaurant r ON o.restaurant_id=r.id WHERE o.user_id=? ORDER BY o.ordered_at DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapOrder(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.username, r.name as restaurant_name FROM orders o LEFT JOIN users u ON o.user_id=u.id LEFT JOIN restaurant r ON o.restaurant_id=r.id ORDER BY o.ordered_at DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapOrder(rs));
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public int updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET order_status=?" + ("delivered".equals(status) ? ", delivered_at=CURRENT_TIMESTAMP" : "") + " WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status); ps.setInt(2, orderId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public int updatePaymentStatus(int orderId, String status) {
        String sql = "UPDATE orders SET payment_status=? WHERE id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status); ps.setInt(2, orderId); return ps.executeUpdate();
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); return 0; }
    }

    @Override
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id=?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id")); item.setOrderId(rs.getInt("order_id"));
                    item.setMenuId(rs.getInt("menu_id")); item.setItemName(rs.getString("item_name"));
                    item.setQuantity(rs.getInt("quantity")); item.setUnitPrice(rs.getDouble("unit_price"));
                    item.setTotalPrice(rs.getDouble("total_price"));
                    items.add(item);
                }
            }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return items;
    }

    @Override
    public int countOrders() {
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM orders"); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return 0;
    }

    @Override
    public double getTotalRevenue() {
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COALESCE(SUM(grand_total),0) FROM orders WHERE payment_status='paid'"); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return 0;
    }

    @Override
    public List<Order> getRecentOrders(int limit) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.username, r.name as restaurant_name FROM orders o LEFT JOIN users u ON o.user_id=u.id LEFT JOIN restaurant r ON o.restaurant_id=r.id ORDER BY o.ordered_at DESC LIMIT ?";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapOrder(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.username, r.name as restaurant_name FROM orders o LEFT JOIN users u ON o.user_id=u.id LEFT JOIN restaurant r ON o.restaurant_id=r.id WHERE o.order_status=? ORDER BY o.ordered_at DESC";
        try (Connection con = DbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(mapOrder(rs)); }
        } catch (SQLException e) { LOGGER.log(Level.SEVERE, "Error", e); }
        return list;
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setId(rs.getInt("id")); o.setUserId(rs.getInt("user_id")); o.setRestaurantId(rs.getInt("restaurant_id"));
        o.setTotalAmount(rs.getDouble("total_amount")); o.setDeliveryFee(rs.getDouble("delivery_fee"));
        o.setTaxAmount(rs.getDouble("tax_amount")); o.setGrandTotal(rs.getDouble("grand_total"));
        o.setDeliveryAddress(rs.getString("delivery_address")); o.setPaymentMethod(rs.getString("payment_method"));
        o.setPaymentStatus(rs.getString("payment_status")); o.setOrderStatus(rs.getString("order_status"));
        o.setSpecialInstructions(rs.getString("special_instructions")); o.setEstimatedDelivery(rs.getString("estimated_delivery"));
        o.setOrderedAt(rs.getTimestamp("ordered_at")); o.setDeliveredAt(rs.getTimestamp("delivered_at"));
        try { o.setUsername(rs.getString("username")); o.setRestaurantName(rs.getString("restaurant_name")); } catch (SQLException ignored) {}
        return o;
    }
}
