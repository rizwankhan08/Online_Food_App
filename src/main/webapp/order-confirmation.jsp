<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.Order, com.model.OrderItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Confirmed - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
<style>
@keyframes popIn { 0%{transform:scale(0)} 70%{transform:scale(1.2)} 100%{transform:scale(1)} }
.success-icon { animation: popIn 0.6s ease forwards; display:inline-block; }
</style>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<%
    Order confirmedOrder = (Order) request.getAttribute("order");
    if (confirmedOrder == null) { response.sendRedirect(ctx + "/restaurants"); return; }
%>
<div class="container" style="max-width:700px;text-align:center;padding-top:40px">
    <div class="success-icon" style="font-size:5rem;margin-bottom:20px">&#9989;</div>
    <h1 style="font-size:2rem;margin-bottom:8px">Order Placed Successfully!</h1>
    <p style="color:var(--text-secondary);margin-bottom:30px">Your order #<strong><%= confirmedOrder.getId() %></strong> has been confirmed.</p>

    <!-- Delivery Tracker -->
    <div class="card" style="padding:24px;margin-bottom:20px">
        <h3 style="margin-bottom:20px">Delivery Status</h3>
        <div class="status-tracker">
            <div class="status-step completed">
                <div class="step-icon">&#10003;</div>
                <div class="step-label">Placed</div>
            </div>
            <div class="status-step active">
                <div class="step-icon">&#128203;</div>
                <div class="step-label">Confirmed</div>
            </div>
            <div class="status-step">
                <div class="step-icon">&#128293;</div>
                <div class="step-label">Preparing</div>
            </div>
            <div class="status-step">
                <div class="step-icon">&#128666;</div>
                <div class="step-label">On the way</div>
            </div>
            <div class="status-step">
                <div class="step-icon">&#127881;</div>
                <div class="step-label">Delivered</div>
            </div>
        </div>
        <p style="color:var(--success);margin-top:10px">&#9200; Estimated delivery: <strong><%= confirmedOrder.getEstimatedDelivery() %></strong></p>
    </div>

    <!-- Order Details -->
    <div class="card" style="padding:20px;margin-bottom:20px;text-align:left">
        <h3 style="margin-bottom:14px">Order Details</h3>
        <% if (confirmedOrder.getOrderItems() != null) {
            for (OrderItem oi : confirmedOrder.getOrderItems()) { %>
        <div style="display:flex;justify-content:space-between;padding:8px 0;border-bottom:1px solid var(--border);font-size:0.9rem">
            <span><%= oi.getItemName() %> x<%= oi.getQuantity() %></span>
            <span>&#8377;<%= String.format("%.2f", oi.getTotalPrice()) %></span>
        </div>
        <% } } %>
        <div style="display:flex;justify-content:space-between;padding:8px 0;font-size:0.9rem;color:var(--text-secondary)">
            <span>Delivery Fee</span><span>&#8377;<%= String.format("%.2f", confirmedOrder.getDeliveryFee()) %></span>
        </div>
        <div style="display:flex;justify-content:space-between;padding:8px 0;font-size:0.9rem;color:var(--text-secondary)">
            <span>GST</span><span>&#8377;<%= String.format("%.2f", confirmedOrder.getTaxAmount()) %></span>
        </div>
        <div style="display:flex;justify-content:space-between;padding-top:10px;font-weight:700;font-size:1.1rem;border-top:1px solid var(--border)">
            <span>Grand Total</span><span class="price">&#8377;<%= String.format("%.2f", confirmedOrder.getGrandTotal()) %></span>
        </div>
        <div style="margin-top:12px;color:var(--text-secondary);font-size:0.9rem">
            <p>&#128205; <strong>Delivery to:</strong> <%= confirmedOrder.getDeliveryAddress() %></p>
            <p>&#128179; <strong>Payment:</strong> <%= confirmedOrder.getPaymentMethod().toUpperCase() %></p>
        </div>
    </div>

    <div style="display:flex;gap:12px;justify-content:center">
        <a href="<%= ctx %>/orders" class="btn btn-outline">View All Orders</a>
        <a href="<%= ctx %>/restaurants" class="btn btn-primary">&#127829; Order More</a>
    </div>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
