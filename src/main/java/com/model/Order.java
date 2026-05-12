package com.model;

import java.sql.Timestamp;
import java.util.List;

/**
 * Order model representing a customer's food order.
 */
public class Order {

    private int id;
    private int userId;
    private int restaurantId;
    private double totalAmount;
    private double deliveryFee;
    private double taxAmount;
    private double grandTotal;
    private String deliveryAddress;
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;
    private String specialInstructions;
    private String estimatedDelivery;
    private Timestamp orderedAt;
    private Timestamp deliveredAt;

    // For display purposes
    private String username;
    private String restaurantName;
    private List<OrderItem> orderItems;

    // Default constructor
    public Order() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public double getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(double deliveryFee) { this.deliveryFee = deliveryFee; }

    public double getTaxAmount() { return taxAmount; }
    public void setTaxAmount(double taxAmount) { this.taxAmount = taxAmount; }

    public double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(double grandTotal) { this.grandTotal = grandTotal; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }

    public String getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(String estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    public Timestamp getOrderedAt() { return orderedAt; }
    public void setOrderedAt(Timestamp orderedAt) { this.orderedAt = orderedAt; }

    public Timestamp getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(Timestamp deliveredAt) { this.deliveredAt = deliveredAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    /**
     * Returns a user-friendly status badge color for the UI.
     */
    public String getStatusColor() {
        return switch (orderStatus != null ? orderStatus : "") {
            case "placed" -> "info";
            case "confirmed" -> "primary";
            case "preparing", "cooking" -> "warning";
            case "out_for_delivery" -> "secondary";
            case "delivered" -> "success";
            case "cancelled" -> "danger";
            default -> "light";
        };
    }

    /**
     * Returns a user-friendly display label for the order status.
     */
    public String getStatusLabel() {
        return switch (orderStatus != null ? orderStatus : "") {
            case "placed" -> "Order Placed";
            case "confirmed" -> "Confirmed";
            case "preparing" -> "Preparing";
            case "cooking" -> "Cooking";
            case "out_for_delivery" -> "Out for Delivery";
            case "delivered" -> "Delivered";
            case "cancelled" -> "Cancelled";
            default -> "Unknown";
        };
    }
}
