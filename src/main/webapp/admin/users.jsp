<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin - Users</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
</head>
<body>
<%@ include file="/includes/navbar.jsp" %>
<div class="admin-layout">
<%@ include file="sidebar.jsp" %>
<main class="admin-main">
    <div class="admin-header"><h1>&#128101; Users</h1></div>
    <div class="table-container">
        <table>
            <thead><tr><th>ID</th><th>Username</th><th>Email</th><th>Phone</th><th>Role</th><th>Status</th><th>Joined</th></tr></thead>
            <tbody>
                <% List<User> users = (List<User>) request.getAttribute("users");
                   if (users != null) { for (User u : users) { %>
                <tr>
                    <td>#<%= u.getId() %></td>
                    <td><div style="display:flex;align-items:center;gap:8px">
                        <div style="width:32px;height:32px;border-radius:50%;background:var(--gradient);display:flex;align-items:center;justify-content:center;font-weight:700;font-size:0.8rem"><%= u.getUsername().charAt(0) %></div>
                        <strong><%= u.getUsername() %></strong>
                    </div></td>
                    <td style="color:var(--text-secondary)"><%= u.getEmail() %></td>
                    <td style="color:var(--text-secondary)"><%= u.getPhone() != null ? u.getPhone() : "-" %></td>
                    <td><span class="badge <%= u.isAdmin() ? "badge-warning" : u.isDelivery() ? "badge-info" : "badge-success" %>" style="text-transform:capitalize"><%= u.getRole() %></span></td>
                    <td><span class="badge <%= u.isActive() ? "badge-success" : "badge-danger" %>"><%= u.isActive() ? "Active" : "Inactive" %></span></td>
                    <td style="color:var(--text-muted);font-size:0.8rem"><%= u.getCreatedAt() != null ? u.getCreatedAt().toString().substring(0,10) : "" %></td>
                </tr>
                <% } } %>
            </tbody>
        </table>
    </div>
</main>
</div>
</body>
</html>
