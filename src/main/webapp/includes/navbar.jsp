<%@ page import="com.model.User, com.model.Cart" %>
<%
    User navUser = (User) session.getAttribute("user");
    Cart navCart = (Cart) session.getAttribute("cart");
    int cartCount = (navCart != null) ? navCart.getItems().size() : 0;
    String ctx = request.getContextPath();
%>
<nav class="navbar">
    <a href="<%= ctx %>/restaurants" class="logo">
        <span>&#127829;</span> FoodApp
    </a>

    <form class="nav-search" action="<%= ctx %>/search" method="get">
        <button type="submit">&#128269;</button>
        <input type="text" name="q" placeholder="Search restaurants, dishes..." autocomplete="off">
    </form>

    <div class="nav-links">
        <a href="<%= ctx %>/restaurants">&#127968; Home</a>
        <% if (navUser != null) { %>
            <a href="<%= ctx %>/orders">&#128230; Orders</a>
            <a href="<%= ctx %>/favorites">&#10084; Favorites</a>
            <a href="<%= ctx %>/cart.jsp" class="cart-badge">
                &#128722; Cart
                <% if (cartCount > 0) { %><span class="count"><%= cartCount %></span><% } %>
            </a>
            <a href="<%= ctx %>/profile">&#128100; <%= navUser.getUsername() %></a>
            <% if (navUser.isAdmin()) { %>
                <a href="<%= ctx %>/admin/dashboard" class="btn btn-sm btn-outline">&#9881; Admin</a>
            <% } %>
            <a href="<%= ctx %>/logout" style="color:var(--danger);">&#9211; Logout</a>
        <% } else { %>
            <a href="<%= ctx %>/login.jsp" class="btn btn-sm btn-outline">Login</a>
            <a href="<%= ctx %>/register.jsp" class="btn btn-sm btn-primary">Sign Up</a>
        <% } %>
    </div>
</nav>
