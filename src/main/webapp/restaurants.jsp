<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Restaurant" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Restaurants - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<%@ include file="includes/navbar.jsp" %>

<div class="hero">
    <h1>Hungry? We've got you covered &#127836;</h1>
    <p>Order from the best restaurants near you</p>
</div>

<div class="container">
    <%
        List<String> cuisineTypes = (List<String>) request.getAttribute("cuisineTypes");
        String searchQuery = (String) request.getAttribute("searchQuery");
        String selectedCuisine = (String) request.getAttribute("selectedCuisine");
    %>

    <!-- Filter Bar -->
    <div class="filter-bar">
        <a href="<%= ctx %>/restaurants" class="filter-chip <%= (selectedCuisine == null && searchQuery == null) ? "active" : "" %>">&#127868; All</a>
        <% if (cuisineTypes != null) { for (String cuisine : cuisineTypes) { %>
            <a href="<%= ctx %>/restaurants?cuisine=<%= cuisine %>" class="filter-chip <%= cuisine.equals(selectedCuisine) ? "active" : "" %>"><%= cuisine %></a>
        <% } } %>
    </div>

    <% if (searchQuery != null) { %>
        <div class="section-header">
            <h2 class="section-title">Results for "<%= searchQuery %>"</h2>
            <a href="<%= ctx %>/restaurants" class="btn btn-sm btn-outline">Clear</a>
        </div>
    <% } %>

    <%
        List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
        if (restaurants != null && !restaurants.isEmpty()) {
    %>
    <div class="grid grid-4">
        <% for (Restaurant r : restaurants) { %>
        <div class="card animate-in" onclick="location.href='menu?restaurantId=<%= r.getId() %>'" style="cursor:pointer">
            <div class="card-img-wrapper">
                <img class="card-img" src="<%= ctx %>/<%= r.getImageUrl() %>" alt="<%= r.getName() %>">
                <% if (r.getCuisineType() != null) { %>
                    <span class="badge badge-primary" style="position:absolute;top:10px;left:10px"><%= r.getCuisineType() %></span>
                <% } %>
            </div>
            <div class="card-body">
                <div class="card-title"><%= r.getName() %></div>
                <div class="delivery-info" style="margin:6px 0">
                    <span>&#11088; <strong style="color:var(--success)"><%= r.getRating() %></strong></span>
                    <span>&#9202; <%= r.getDeliveryTime() %></span>
                </div>
                <div class="card-text">&#128205; <%= r.getLocation() %></div>
                <% if (r.getMinOrder() > 0) { %>
                    <div class="card-text" style="margin-top:4px">Min order: &#8377;<%= String.format("%.0f", r.getMinOrder()) %></div>
                <% } %>
            </div>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <div class="empty-state">
        <div class="icon">&#128533;</div>
        <h3>No Restaurants Found</h3>
        <p>Try a different search or filter</p>
        <a href="<%= ctx %>/restaurants" class="btn btn-primary" style="margin-top:16px">Browse All</a>
    </div>
    <% } %>
</div>

<%@ include file="includes/footer.jsp" %>
</body>
</html>