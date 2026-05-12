<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.Cart, com.model.CartItem, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cart - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<%@ include file="includes/navbar.jsp" %>

<div class="container" style="max-width:800px">
    <div class="section-header">
        <h2 class="section-title">&#128722; Your Cart</h2>
    </div>

    <%
        Cart cart2 = (Cart) session.getAttribute("cart");
        if (cart2 != null && !cart2.getItems().isEmpty()) {
    %>
    <div style="display:flex;flex-direction:column;gap:12px">
        <% for (CartItem item : cart2.getItems().values()) { %>
        <div class="card" style="padding:16px">
            <div style="display:flex;align-items:center;justify-content:space-between;flex-wrap:wrap;gap:12px">
                <div>
                    <h3 style="font-size:1.05rem;margin-bottom:4px"><%= item.getName() %></h3>
                    <span class="price">&#8377; <%= String.format("%.0f", item.getPrice()) %></span>
                    <span style="color:var(--text-muted);font-size:0.85rem"> x <%= item.getQuantity() %></span>
                </div>
                <div style="display:flex;align-items:center;gap:6px">
                    <form action="cart" method="post" style="margin:0">
                        <input type="hidden" name="menuId" value="<%= item.getId() %>">
                        <input type="hidden" name="quantity" value="<%= item.getQuantity() - 1 %>">
                        <input type="hidden" name="action" value="update">
                        <button type="submit" class="btn btn-sm btn-danger" style="width:32px;height:32px;padding:0">&#8722;</button>
                    </form>
                    <span style="font-weight:700;min-width:28px;text-align:center"><%= item.getQuantity() %></span>
                    <form action="cart" method="post" style="margin:0">
                        <input type="hidden" name="menuId" value="<%= item.getId() %>">
                        <input type="hidden" name="quantity" value="<%= item.getQuantity() + 1 %>">
                        <input type="hidden" name="action" value="update">
                        <button type="submit" class="btn btn-sm btn-success" style="width:32px;height:32px;padding:0">&#43;</button>
                    </form>
                    <form action="cart" method="post" style="margin:0;margin-left:8px">
                        <input type="hidden" name="menuId" value="<%= item.getId() %>">
                        <input type="hidden" name="quantity" value="0">
                        <input type="hidden" name="action" value="delete">
                        <button type="submit" class="btn btn-sm btn-outline" style="color:var(--danger);border-color:var(--danger)">&#128465;</button>
                    </form>
                </div>
                <div style="width:100%;text-align:right">
                    <strong>&#8377; <%= String.format("%.0f", item.getTotalPrice()) %></strong>
                </div>
            </div>
        </div>
        <% } %>
    </div>

    <!-- Cart Summary -->
    <div class="card" style="padding:20px;margin-top:20px">
        <h3 style="margin-bottom:16px">Bill Details</h3>
        <div style="display:flex;justify-content:space-between;margin-bottom:8px;font-size:0.9rem">
            <span style="color:var(--text-secondary)">Item Total</span>
            <span>&#8377; <%= String.format("%.2f", cart2.getTotalAmount()) %></span>
        </div>
        <div style="display:flex;justify-content:space-between;margin-bottom:8px;font-size:0.9rem">
            <span style="color:var(--text-secondary)">Delivery Fee</span>
            <span>&#8377; 30.00</span>
        </div>
        <div style="display:flex;justify-content:space-between;margin-bottom:8px;font-size:0.9rem">
            <span style="color:var(--text-secondary)">GST (5%)</span>
            <span>&#8377; <%= String.format("%.2f", cart2.getTotalAmount() * 0.05) %></span>
        </div>
        <div style="border-top:1px solid var(--border);padding-top:12px;margin-top:12px;display:flex;justify-content:space-between;font-size:1.1rem;font-weight:700">
            <span>Total</span>
            <span class="price">&#8377; <%= String.format("%.2f", cart2.getTotalAmount() + 30 + cart2.getTotalAmount() * 0.05) %></span>
        </div>
        <a href="<%= request.getContextPath() %>/checkout" class="btn btn-primary btn-block btn-lg" style="margin-top:16px">
            Proceed to Checkout &#10148;
        </a>
    </div>

    <% } else { %>
    <div class="empty-state" style="padding:80px 20px">
        <div class="icon">&#128722;</div>
        <h3>Your cart is empty</h3>
        <p>Looks like you haven't added anything yet</p>
        <a href="<%= request.getContextPath() %>/restaurants" class="btn btn-primary" style="margin-top:20px">
            &#127829; Browse Restaurants
        </a>
    </div>
    <% } %>
</div>

<%@ include file="includes/footer.jsp" %>
</body>
</html>