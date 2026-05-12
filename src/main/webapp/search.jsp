<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Restaurant, com.model.Menu" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Search - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="container">
    <!-- Search Bar -->
    <div style="max-width:600px;margin:0 auto 30px">
        <form action="<%= ctx %>/search" method="get" style="display:flex;gap:10px">
            <input type="text" name="q" class="form-control" placeholder="Search restaurants or dishes..."
                value="<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>"
                style="flex:1;font-size:1rem;padding:14px 20px">
            <button type="submit" class="btn btn-primary btn-lg">&#128269; Search</button>
        </form>
        <div class="filter-bar" style="margin-top:12px;justify-content:center">
            <a href="?q=<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>" class="filter-chip <%= request.getAttribute("searchType") == null ? "active" : "" %>">All</a>
            <a href="?q=<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>&type=restaurant" class="filter-chip <%= "restaurant".equals(request.getAttribute("searchType")) ? "active" : "" %>">&#127869; Restaurants</a>
            <a href="?q=<%= request.getAttribute("searchQuery") != null ? request.getAttribute("searchQuery") : "" %>&type=menu" class="filter-chip <%= "menu".equals(request.getAttribute("searchType")) ? "active" : "" %>">&#127860; Dishes</a>
        </div>
    </div>

    <%
        List<Restaurant> rResults = (List<Restaurant>) request.getAttribute("restaurantResults");
        List<Menu> mResults = (List<Menu>) request.getAttribute("menuResults");
        String sq = (String) request.getAttribute("searchQuery");
    %>

    <% if (sq != null) { %>

    <!-- Restaurant Results -->
    <% if (rResults != null && !rResults.isEmpty()) { %>
    <div class="section-header"><h3 class="section-title">&#127869; Restaurants (<%= rResults.size() %>)</h3></div>
    <div class="grid grid-4" style="margin-bottom:30px">
        <% for (Restaurant r : rResults) { %>
        <div class="card animate-in" onclick="location.href='<%= ctx %>/menu?restaurantId=<%= r.getId() %>'" style="cursor:pointer">
            <div class="card-img-wrapper">
                <img class="card-img" src="<%= ctx %>/<%= r.getImageUrl() %>" alt="<%= r.getName() %>">
            </div>
            <div class="card-body">
                <div class="card-title"><%= r.getName() %></div>
                <div class="delivery-info" style="margin:4px 0">
                    <span>&#11088; <strong style="color:var(--success)"><%= r.getRating() %></strong></span>
                    <span>&#9202; <%= r.getDeliveryTime() %></span>
                </div>
                <div class="card-text">&#128205; <%= r.getLocation() %></div>
            </div>
        </div>
        <% } %>
    </div>
    <% } %>

    <!-- Menu Results -->
    <% if (mResults != null && !mResults.isEmpty()) { %>
    <div class="section-header"><h3 class="section-title">&#127860; Dishes (<%= mResults.size() %>)</h3></div>
    <div class="grid grid-4">
        <% for (Menu m : mResults) { %>
        <div class="card animate-in">
            <div class="card-img-wrapper">
                <img class="card-img" src="<%= ctx %>/<%= m.getImageUrl() %>" alt="<%= m.getName() %>">
                <span class="badge <%= m.isVeg() ? "badge-veg" : "badge-nonveg" %>" style="position:absolute;top:10px;left:10px"><%= m.isVeg() ? "Veg" : "Non-Veg" %></span>
            </div>
            <div class="card-body">
                <div class="card-title"><%= m.getName() %></div>
                <p class="card-text" style="font-size:0.8rem"><%= m.getDescription() %></p>
                <div style="display:flex;justify-content:space-between;align-items:center;margin-top:10px">
                    <span class="price">&#8377;<%= String.format("%.0f", m.getPrice()) %></span>
                    <form action="cart" method="post" style="margin:0">
                        <input type="hidden" name="menuId" value="<%= m.getId() %>">
                        <input type="hidden" name="quantity" value="1">
                        <input type="hidden" name="action" value="add">
                        <button type="submit" class="btn btn-primary btn-sm">+ Add</button>
                    </form>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    <% } %>

    <% if ((rResults == null || rResults.isEmpty()) && (mResults == null || mResults.isEmpty())) { %>
    <div class="empty-state">
        <div class="icon">&#128269;</div>
        <h3>No results for "<%= sq %>"</h3>
        <p>Try searching for something else</p>
    </div>
    <% } %>
    <% } %>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
