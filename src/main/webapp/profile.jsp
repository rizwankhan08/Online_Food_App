<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Profile - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="container" style="max-width:700px">
    <div class="section-header"><h2 class="section-title">&#128100; My Profile</h2></div>

    <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("success") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error"><%= request.getAttribute("error") %></div>
    <% } %>

    <!-- Profile Avatar -->
    <div class="card" style="padding:24px;text-align:center;margin-bottom:16px">
        <div style="width:80px;height:80px;border-radius:50%;background:var(--gradient);display:flex;align-items:center;justify-content:center;font-size:2rem;font-weight:800;margin:0 auto 12px">
            <%= navUser.getUsername().charAt(0) %>
        </div>
        <h2><%= navUser.getUsername() %></h2>
        <p style="color:var(--text-secondary)"><%= navUser.getEmail() %></p>
        <span class="badge badge-<%= navUser.isAdmin() ? "warning" : "success" %>" style="margin-top:6px;text-transform:capitalize"><%= navUser.getRole() %></span>
    </div>

    <!-- Edit Profile -->
    <div class="card" style="padding:20px;margin-bottom:16px">
        <h3 style="margin-bottom:16px">Edit Profile</h3>
        <form action="<%= ctx %>/profile" method="post">
            <input type="hidden" name="action" value="updateProfile">
            <div class="form-group">
                <label class="form-label">Username</label>
                <input type="text" name="username" class="form-control" value="<%= navUser.getUsername() %>" required>
            </div>
            <div class="form-group">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" value="<%= navUser.getEmail() %>" required>
            </div>
            <div class="form-group">
                <label class="form-label">Phone</label>
                <input type="tel" name="phone" class="form-control" value="<%= navUser.getPhone() != null ? navUser.getPhone() : "" %>" placeholder="10-digit phone number">
            </div>
            <div class="form-group">
                <label class="form-label">Address</label>
                <input type="text" name="address" class="form-control" value="<%= navUser.getAddress() != null ? navUser.getAddress() : "" %>">
            </div>
            <button type="submit" class="btn btn-primary">Save Changes</button>
        </form>
    </div>

    <!-- Change Password -->
    <div class="card" style="padding:20px">
        <h3 style="margin-bottom:16px">&#128274; Change Password</h3>
        <form action="<%= ctx %>/profile" method="post">
            <input type="hidden" name="action" value="changePassword">
            <div class="form-group">
                <label class="form-label">Current Password</label>
                <input type="password" name="currentPassword" class="form-control" required>
            </div>
            <div class="form-group">
                <label class="form-label">New Password</label>
                <input type="password" name="newPassword" class="form-control" required minlength="6">
            </div>
            <div class="form-group">
                <label class="form-label">Confirm New Password</label>
                <input type="password" name="confirmPassword" class="form-control" required minlength="6">
            </div>
            <button type="submit" class="btn btn-danger">Change Password</button>
        </form>
    </div>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
