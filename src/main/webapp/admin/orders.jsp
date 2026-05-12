<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Order" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin - Orders</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
</head>
<body>
<%@ include file="/includes/navbar.jsp" %>
<div class="admin-layout">
<%@ include file="sidebar.jsp" %>
<main class="admin-main">
    <div class="admin-header"><h1>&#128230; Orders</h1></div>

    <!-- Status Filter -->
    <div class="filter-bar" style="margin-bottom:16px">
        <a href="<%= ctx %>/admin/orders" class="filter-chip">All</a>
        <% String[] statuses = {"placed","confirmed","preparing","cooking","out_for_delivery","delivered","cancelled"};
           for (String s : statuses) { %>
        <a href="<%= ctx %>/admin/orders?status=<%= s %>" class="filter-chip"><%= s.replace("_"," ") %></a>
        <% } %>
    </div>

    <div class="table-container">
        <table>
            <thead><tr><th>ID</th><th>User</th><th>Restaurant</th><th>Amount</th><th>Payment</th><th>Status</th><th>Date</th><th>Actions</th></tr></thead>
            <tbody>
                <% List<Order> orders = (List<Order>) request.getAttribute("orders");
                   if (orders != null) { for (Order o : orders) { %>
                <tr>
                    <td>#<%= o.getId() %></td>
                    <td><%= o.getUsername() %></td>
                    <td><%= o.getRestaurantName() %></td>
                    <td class="price">&#8377;<%= String.format("%.2f", o.getGrandTotal()) %></td>
                    <td>
                        <span style="text-transform:uppercase;font-size:0.8rem"><%= o.getPaymentMethod() %></span><br>
                        <span class="badge badge-<%= "paid".equals(o.getPaymentStatus()) ? "success" : "warning" %>" style="font-size:0.7rem"><%= o.getPaymentStatus() %></span>
                    </td>
                    <td><span class="badge badge-<%= o.getStatusColor() %>"><%= o.getStatusLabel() %></span></td>
                    <td style="color:var(--text-muted);font-size:0.8rem"><%= o.getOrderedAt() != null ? o.getOrderedAt().toString().substring(0,16) : "" %></td>
                    <td>
                        <form action="<%= ctx %>/admin/order/status" method="post" style="display:flex;gap:4px">
                            <input type="hidden" name="orderId" value="<%= o.getId() %>">
                            <select name="status" class="form-control" style="padding:4px 8px;font-size:0.8rem;width:130px">
                                <% for (String s : statuses) { %>
                                <option value="<%= s %>" <%= s.equals(o.getOrderStatus()) ? "selected" : "" %>><%= s.replace("_"," ") %></option>
                                <% } %>
                            </select>
                            <button type="submit" class="btn btn-sm btn-primary">&#10003;</button>
                        </form>
                    </td>
                </tr>
                <% } } %>
            </tbody>
        </table>
    </div>
</main>
</div>
</body>
</html>
