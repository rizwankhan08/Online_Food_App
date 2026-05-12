<%@ page import="com.model.User" %>
<%
    User sidebarUser = (User) session.getAttribute("user");
    String sideCtx = request.getContextPath();
    String currentPath = request.getRequestURI();
%>
<aside class="admin-sidebar">
    <div class="sidebar-brand">&#9881; Admin Panel</div>
    <nav class="sidebar-nav">
        <a href="<%= sideCtx %>/admin/dashboard" class="sidebar-link <%= currentPath.contains("/dashboard") ? "active" : "" %>">&#128202; Dashboard</a>
        <a href="<%= sideCtx %>/admin/restaurants" class="sidebar-link <%= currentPath.contains("/restaurants") ? "active" : "" %>">&#127869; Restaurants</a>
        <a href="<%= sideCtx %>/admin/menu" class="sidebar-link <%= currentPath.contains("/menu") ? "active" : "" %>">&#127860; Menu Items</a>
        <a href="<%= sideCtx %>/admin/orders" class="sidebar-link <%= currentPath.contains("/orders") ? "active" : "" %>">&#128230; Orders</a>
        <a href="<%= sideCtx %>/admin/users" class="sidebar-link <%= currentPath.contains("/users") ? "active" : "" %>">&#128101; Users</a>
        <div style="border-top:1px solid var(--border);margin:10px 0"></div>
        <a href="<%= sideCtx %>/restaurants" class="sidebar-link">&#127968; View Site</a>
        <a href="<%= sideCtx %>/logout" class="sidebar-link" style="color:var(--danger)">&#9211; Logout</a>
    </nav>
</aside>
