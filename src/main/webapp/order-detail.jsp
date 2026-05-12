<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.Order, com.model.OrderItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Detail - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<%
    Order od = (Order) request.getAttribute("order");
    if (od == null) { response.sendRedirect(ctx + "/orders"); return; }
    String[] steps = {"placed","confirmed","preparing","cooking","out_for_delivery","delivered"};
    String[] labels = {"Placed","Confirmed","Preparing","Cooking","On the way","Delivered"};
    String[] icons  = {"📋","✅","🍳","🔥","🚗","🎉"};
    int currentStep = 0;
    for (int s = 0; s < steps.length; s++) { if (steps[s].equals(od.getOrderStatus())) currentStep = s; }
%>
<div class="container" style="max-width:750px">
    <div class="section-header">
        <h2 class="section-title">Order #<%= od.getId() %></h2>
        <span class="badge badge-<%= od.getStatusColor() %>"><%= od.getStatusLabel() %></span>
    </div>

    <!-- Status Tracker -->
    <div class="card" style="padding:24px;margin-bottom:20px">
        <div class="status-tracker">
            <% for (int s = 0; s < steps.length; s++) { %>
            <div class="status-step <%= s < currentStep ? "completed" : (s == currentStep ? "active" : "") %>">
                <div class="step-icon"><%= icons[s] %></div>
                <div class="step-label"><%= labels[s] %></div>
            </div>
            <% } %>
        </div>
    </div>

    <!-- Items -->
    <div class="card" style="padding:20px;margin-bottom:16px">
        <h3 style="margin-bottom:14px">Items Ordered</h3>
        <% if (od.getOrderItems() != null) {
            for (OrderItem oi : od.getOrderItems()) { %>
        <div style="display:flex;justify-content:space-between;padding:8px 0;border-bottom:1px solid var(--border);font-size:0.9rem">
            <span><%= oi.getItemName() %> <span style="color:var(--text-muted)">x<%= oi.getQuantity() %></span></span>
            <span>&#8377;<%= String.format("%.2f", oi.getTotalPrice()) %></span>
        </div>
        <% } } %>
        <div style="display:flex;justify-content:space-between;padding:8px 0;font-size:0.9rem;color:var(--text-secondary)"><span>Delivery Fee</span><span>&#8377;<%= String.format("%.2f", od.getDeliveryFee()) %></span></div>
        <div style="display:flex;justify-content:space-between;padding:8px 0;font-size:0.9rem;color:var(--text-secondary)"><span>GST</span><span>&#8377;<%= String.format("%.2f", od.getTaxAmount()) %></span></div>
        <div style="display:flex;justify-content:space-between;padding-top:10px;font-weight:700;font-size:1.1rem;border-top:1px solid var(--border)">
            <span>Grand Total</span><span class="price">&#8377;<%= String.format("%.2f", od.getGrandTotal()) %></span>
        </div>
    </div>

    <!-- Delivery Info -->
    <div class="card" style="padding:20px;margin-bottom:16px">
        <h3 style="margin-bottom:12px">Delivery Info</h3>
        <p style="color:var(--text-secondary);font-size:0.9rem">&#128205; <strong>Address:</strong> <%= od.getDeliveryAddress() %></p>
        <p style="color:var(--text-secondary);font-size:0.9rem;margin-top:6px">&#128179; <strong>Payment:</strong> <%= od.getPaymentMethod().toUpperCase() %> — <span class="badge badge-<%= "paid".equals(od.getPaymentStatus()) ? "success" : "warning" %>"><%= od.getPaymentStatus().toUpperCase() %></span></p>
        <% if (od.getSpecialInstructions() != null && !od.getSpecialInstructions().isEmpty()) { %>
        <p style="color:var(--text-secondary);font-size:0.9rem;margin-top:6px">&#128221; <strong>Instructions:</strong> <%= od.getSpecialInstructions() %></p>
        <% } %>
    </div>

    <div style="display:flex;gap:10px">
        <a href="<%= ctx %>/orders" class="btn btn-outline">&#8592; Back to Orders</a>
        <a href="<%= ctx %>/restaurants" class="btn btn-primary">&#127829; Order Again</a>
    </div>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
