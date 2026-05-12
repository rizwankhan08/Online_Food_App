<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Order" %>
<%@ include file="/includes/navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard - FoodApp</title>
<link rel="stylesheet" href="<%= ctx %>/css/common.css">
<link rel="stylesheet" href="<%= ctx %>/css/admin.css">
</head>
<body>
<div class="admin-layout">
    <%@ include file="sidebar.jsp" %>
    <main class="admin-main">
        <div class="admin-header">
            <h1>&#128202; Dashboard</h1>
            <span style="color:var(--text-muted);font-size:0.85rem">Welcome back, Admin</span>
        </div>

        <!-- Stats -->
        <div class="grid grid-4" style="margin-bottom:24px">
            <div class="stat-card">
                <div class="stat-icon">&#128101;</div>
                <div class="stat-value"><%= request.getAttribute("totalUsers") %></div>
                <div class="stat-label">Total Users</div>
            </div>
            <div class="stat-card" style="border-color:rgba(59,130,246,0.3)">
                <div class="stat-icon">&#127869;</div>
                <div class="stat-value"><%= request.getAttribute("totalRestaurants") %></div>
                <div class="stat-label">Restaurants</div>
            </div>
            <div class="stat-card" style="border-color:rgba(245,158,11,0.3)">
                <div class="stat-icon">&#128230;</div>
                <div class="stat-value"><%= request.getAttribute("totalOrders") %></div>
                <div class="stat-label">Total Orders</div>
            </div>
            <div class="stat-card" style="border-color:rgba(16,185,129,0.3)">
                <div class="stat-icon">&#128176;</div>
                <div class="stat-value">&#8377;<%= String.format("%.0f", (Double)request.getAttribute("totalRevenue")) %></div>
                <div class="stat-label">Revenue</div>
            </div>
        </div>

        <!-- Recent Orders -->
        <div class="card" style="padding:20px">
            <div class="section-header">
                <h3 class="section-title">Recent Orders</h3>
                <a href="<%= ctx %>/admin/orders" class="btn btn-sm btn-outline">View All</a>
            </div>
            <div class="table-container">
                <table>
                    <thead>
                        <tr><th>ID</th><th>User</th><th>Restaurant</th><th>Amount</th><th>Status</th><th>Date</th></tr>
                    </thead>
                    <tbody>
                        <% List<Order> recent = (List<Order>) request.getAttribute("recentOrders");
                           if (recent != null) { for (Order o : recent) { %>
                        <tr>
                            <td>#<%= o.getId() %></td>
                            <td><%= o.getUsername() %></td>
                            <td><%= o.getRestaurantName() %></td>
                            <td class="price">&#8377;<%= String.format("%.2f", o.getGrandTotal()) %></td>
                            <td><span class="badge badge-<%= o.getStatusColor() %>"><%= o.getStatusLabel() %></span></td>
                            <td style="color:var(--text-muted);font-size:0.8rem"><%= o.getOrderedAt() != null ? o.getOrderedAt().toString().substring(0,16) : "" %></td>
                        </tr>
                        <% } } %>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>
</body>
</html>
