<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Favorite" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Favorites - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<div class="container">
    <div class="section-header"><h2 class="section-title">&#10084; My Favorites</h2></div>

    <%
        List<Favorite> favRestaurants = (List<Favorite>) request.getAttribute("favRestaurants");
        List<Favorite> favMenuItems  = (List<Favorite>) request.getAttribute("favMenuItems");
    %>

    <!-- Favorite Restaurants -->
    <h3 style="margin-bottom:14px">&#127869; Favorite Restaurants</h3>
    <% if (favRestaurants != null && !favRestaurants.isEmpty()) { %>
    <div class="grid grid-4" style="margin-bottom:30px">
        <% for (Favorite fav : favRestaurants) { %>
        <div class="card animate-in" style="cursor:pointer" onclick="location.href='<%= ctx %>/menu?restaurantId=<%= fav.getRestaurantId() %>'">
            <div class="card-img-wrapper">
                <img class="card-img" src="<%= ctx %>/<%= fav.getRestaurantImage() %>" alt="<%= fav.getRestaurantName() %>">
            </div>
            <div class="card-body">
                <div class="card-title"><%= fav.getRestaurantName() %></div>
                <form action="<%= ctx %>/favorites" method="post" style="margin-top:10px">
                    <input type="hidden" name="action" value="toggle">
                    <input type="hidden" name="restaurantId" value="<%= fav.getRestaurantId() %>">
                    <input type="hidden" name="redirect" value="<%= ctx %>/favorites">
                    <button type="submit" class="btn btn-sm btn-danger btn-block">&#10084; Remove</button>
                </form>
            </div>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <div class="empty-state" style="padding:30px">
        <div class="icon">&#127869;</div>
        <p>No favorite restaurants yet</p>
        <a href="<%= ctx %>/restaurants" class="btn btn-primary" style="margin-top:10px">Browse Restaurants</a>
    </div>
    <% } %>

    <!-- Favorite Menu Items -->
    <h3 style="margin-bottom:14px">&#127860; Favorite Dishes</h3>
    <% if (favMenuItems != null && !favMenuItems.isEmpty()) { %>
    <div class="grid grid-4">
        <% for (Favorite fav : favMenuItems) { %>
        <div class="card animate-in">
            <div class="card-img-wrapper">
                <img class="card-img" src="<%= ctx %>/<%= fav.getMenuImage() %>" alt="<%= fav.getMenuName() %>">
            </div>
            <div class="card-body">
                <div class="card-title"><%= fav.getMenuName() %></div>
                <span class="price">&#8377;<%= String.format("%.0f", fav.getMenuPrice()) %></span>
                <form action="<%= ctx %>/favorites" method="post" style="margin-top:10px">
                    <input type="hidden" name="action" value="toggle">
                    <input type="hidden" name="menuId" value="<%= fav.getMenuId() %>">
                    <input type="hidden" name="redirect" value="<%= ctx %>/favorites">
                    <button type="submit" class="btn btn-sm btn-danger btn-block">&#10084; Remove</button>
                </form>
            </div>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <div class="empty-state" style="padding:30px">
        <div class="icon">&#127860;</div>
        <p>No favorite dishes yet</p>
    </div>
    <% } %>
</div>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
