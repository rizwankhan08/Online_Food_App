<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Order" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Orders - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="container">
    <div class="section-header">
        <h2 class="section-title">&#128230; My Orders</h2>
    </div>
    <%
        List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders != null && !orders.isEmpty()) {
    %>
    <div style="display:flex;flex-direction:column;gap:14px">
        <% for (Order o : orders) { %>
        <div class="card" style="padding:18px">
            <div style="display:flex;align-items:center;justify-content:space-between;flex-wrap:wrap;gap:10px">
                <div>
                    <div style="font-weight:700;font-size:1rem">Order #<%= o.getId() %></div>
                    <div style="color:var(--text-secondary);font-size:0.85rem;margin-top:2px">
                        &#127869; <%= o.getRestaurantName() != null ? o.getRestaurantName() : "Restaurant" %>
                    </div>
                    <div style="color:var(--text-muted);font-size:0.8rem;margin-top:2px">
                        <%= o.getOrderedAt() != null ? o.getOrderedAt().toString().substring(0,16) : "" %>
                    </div>
                </div>
                <div style="text-align:right">
                    <span class="badge badge-<%= o.getStatusColor() %>" style="font-size:0.8rem"><%= o.getStatusLabel() %></span>
                    <div class="price" style="margin-top:6px;font-size:1.1rem">&#8377;<%= String.format("%.2f", o.getGrandTotal()) %></div>
                    <div style="color:var(--text-muted);font-size:0.8rem;text-transform:capitalize"><%= o.getPaymentMethod() %></div>
                </div>
            </div>
            <div style="display:flex;gap:8px;margin-top:12px">
                <a href="<%= ctx %>/orders?id=<%= o.getId() %>" class="btn btn-sm btn-outline">View Details</a>
                <% if ("delivered".equals(o.getOrderStatus())) { %>
                    <a href="<%= ctx %>/restaurants" class="btn btn-sm btn-primary">&#127829; Reorder</a>
                <% } %>
            </div>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <div class="empty-state">
        <div class="icon">&#128230;</div>
        <h3>No orders yet</h3>
        <p>Start ordering your favourite food!</p>
        <a href="<%= ctx %>/restaurants" class="btn btn-primary" style="margin-top:16px">&#127829; Browse Restaurants</a>
    </div>
    <% } %>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
