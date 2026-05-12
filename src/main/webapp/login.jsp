<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<div class="auth-container">
    <div class="auth-card animate-in">
        <div style="text-align:center;font-size:3rem;margin-bottom:10px">&#127829;</div>
        <h2>Welcome Back!</h2>
        <p class="subtitle">Login to order your favorite food</p>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error"><%= request.getAttribute("error") %></div>
        <% } %>
        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= request.getAttribute("success") %></div>
        <% } %>

        <form action="${pageContext.request.contextPath}/LoginServlet" method="post" id="loginForm">
            <div class="form-group">
                <label class="form-label">Username</label>
                <input type="text" name="username" class="form-control" placeholder="Enter your username" required>
            </div>
            <div class="form-group">
                <label class="form-label">Password</label>
                <input type="password" name="password" class="form-control" placeholder="Enter your password" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block btn-lg" style="margin-top:8px">Login &#10148;</button>
        </form>

        <p class="auth-link">
            Don't have an account? <a href="register.jsp">Sign Up</a>
        </p>
    </div>
</div>
</body>
</html>
