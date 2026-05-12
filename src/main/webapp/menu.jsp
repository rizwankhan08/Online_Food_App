<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ page import="java.util.*, com.model.Menu, com.model.Restaurant, com.model.Review" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Menu - FoodApp</title>
            <link rel="stylesheet" href="css/common.css">
        </head>

        <body>
            <%@ include file="includes/navbar.jsp" %>
                <% Restaurant restaurant=(Restaurant) request.getAttribute("restaurant"); List<Menu> menuList = (List
                    <Menu>) request.getAttribute("menuList");
                        List<Review> reviews = (List<Review>) request.getAttribute("reviews");
                                String rName = restaurant != null ? restaurant.getName() : "Menu";
                                %>

                                <% if (restaurant !=null) { %>
                                    <div
                                        style="background:var(--bg-card);padding:30px;border-bottom:1px solid var(--border)">
                                        <div class="container"
                                            style="display:flex;align-items:center;gap:20px;padding:0">
                                            <img src="<%= ctx %>/<%= restaurant.getImageUrl() %>" alt="<%= rName %>"
                                                style="width:120px;height:120px;border-radius:var(--radius);object-fit:cover">
                                            <div>
                                                <h1 style="font-size:1.8rem;font-weight:800">
                                                    <%= rName %>
                                                </h1>
                                                <% if (restaurant.getDescription() !=null) { %>
                                                    <p style="color:var(--text-secondary);margin:4px 0">
                                                        <%= restaurant.getDescription() %>
                                                    </p>
                                                    <% } %>
                                                        <div class="delivery-info" style="margin-top:8px">
                                                            <span>&#11088; <strong style="color:var(--success)">
                                                                    <%= restaurant.getRating() %>
                                                                </strong></span>
                                                            <span>&#9202; <%= restaurant.getDeliveryTime() %></span>
                                                            <span>&#128205; <%= restaurant.getLocation() %></span>
                                                            <% if (restaurant.getCuisineType() !=null) { %><span
                                                                    class="badge badge-primary">
                                                                    <%= restaurant.getCuisineType() %>
                                                                </span>
                                                                <% } %>
                                                        </div>
                                            </div>
                                        </div>
                                    </div>
                                    <% } %>

                                        <div class="container">
                                            <div class="section-header">
                                                <h2 class="section-title">&#127860; Menu</h2>
                                                <span style="color:var(--text-secondary)">
                                                    <%= menuList !=null ? menuList.size() : 0 %> items
                                                </span>
                                            </div>

                                            <% if (menuList !=null && !menuList.isEmpty()) { %>
                                                <div class="grid grid-3">
                                                    <% for (Menu item : menuList) { %>
                                                        <div class="card animate-in">
                                                            <div class="card-img-wrapper">
                                                                <img class="card-img"
                                                                    src="<%= ctx %>/<%= item.getImageUrl() %>"
                                                                    alt="<%= item.getName() %>">
                                                                <span class="badge <%= item.isVeg() ? " badge-veg"
                                                                    : "badge-nonveg" %>"
                                                                    style="position:absolute;top:10px;left:10px">
                                                                    <%= item.isVeg() ? "&#9679; Veg" : "&#9679; Non-Veg"
                                                                        %>
                                                                </span>
                                                            </div>
                                                            <div class="card-body">
                                                                <div class="card-title">
                                                                    <%= item.getName() %>
                                                                </div>
                                                                <p class="card-text">
                                                                    <%= item.getDescription() %>
                                                                </p>
                                                                <div
                                                                    style="display:flex;align-items:center;gap:8px;margin:8px 0">
                                                                    <span>&#11088; <%= item.getRating() %></span>
                                                                    <% if (item.getCategory() !=null) { %><span
                                                                            class="badge badge-info">
                                                                            <%= item.getCategory() %>
                                                                        </span>
                                                                        <% } %>
                                                                </div>
                                                                <div
                                                                    style="display:flex;align-items:center;justify-content:space-between;margin-top:10px">
                                                                    <span class="price">&#8377; <%=
                                                                            String.format("%.0f", item.getPrice()) %>
                                                                    </span>
                                                                    <form action="cart" method="post" style="margin:0">
                                                                        <input type="hidden" name="menuId"
                                                                            value="<%= item.getId() %>">
                                                                        <input type="hidden" name="quantity" value="1">
                                                                        <input type="hidden" name="restaurantId"
                                                                            value="<%= item.getRestaurantId() %>">
                                                                        <input type="hidden" name="action" value="add">
                                                                        <button type="submit"
                                                                            class="btn btn-primary btn-sm">&#10010;
                                                                            Add</button>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <% } %>
                                                </div>
                                                <% } else { %>
                                                    <div class="empty-state">
                                                        <div class="icon">&#127860;</div>
                                                        <h3>No menu items available</h3>
                                                        <a href="<%= ctx %>/restaurants" class="btn btn-primary"
                                                            style="margin-top:16px">Back to Restaurants</a>
                                                    </div>
                                                    <% } %>

                                                        <!-- Reviews Section -->
                                                        <% if (reviews !=null && !reviews.isEmpty()) { %>
                                                            <div class="section-header" style="margin-top:40px">
                                                                <h2 class="section-title">&#11088; Reviews</h2>
                                                            </div>
                                                            <div style="display:flex;flex-direction:column;gap:12px">
                                                                <% for (Review rev : reviews) { %>
                                                                    <div class="card" style="padding:16px">
                                                                        <div
                                                                            style="display:flex;align-items:center;gap:10px;margin-bottom:8px">
                                                                            <div
                                                                                style="width:36px;height:36px;border-radius:50%;background:var(--gradient);display:flex;align-items:center;justify-content:center;font-weight:700;font-size:0.9rem">
                                                                                <%= rev.getUsername() !=null ?
                                                                                    rev.getUsername().charAt(0) : "U" %>
                                                                            </div>
                                                                            <div>
                                                                                <strong>
                                                                                    <%= rev.getUsername() %>
                                                                                </strong>
                                                                                <div
                                                                                    style="color:var(--accent);font-size:0.85rem">
                                                                                    <%= rev.getStarsHtml() %>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <p
                                                                            style="color:var(--text-secondary);font-size:0.9rem">
                                                                            <%= rev.getComment() %>
                                                                        </p>
                                                                    </div>
                                                                    <% } %>
                                                            </div>
                                                            <% } %>

                                                                <!-- Add Review Form -->
                                                                <% if (navUser !=null && restaurant !=null) { %>
                                                                    <div class="card"
                                                                        style="padding:20px;margin-top:20px">
                                                                        <h3 style="margin-bottom:12px">&#9997; Write a
                                                                            Review</h3>
                                                                        <form action="<%= ctx %>/review" method="post">
                                                                            <input type="hidden" name="restaurantId"
                                                                                value="<%= restaurant.getId() %>">
                                                                            <div class="form-group">
                                                                                <label class="form-label">Rating</label>
                                                                                <select name="rating"
                                                                                    class="form-control" required>
                                                                                    <option value="5">
                                                                                        &#11088;&#11088;&#11088;&#11088;&#11088;
                                                                                        Excellent</option>
                                                                                    <option value="4">
                                                                                        &#11088;&#11088;&#11088;&#11088;
                                                                                        Good</option>
                                                                                    <option value="3">
                                                                                        &#11088;&#11088;&#11088; Average
                                                                                    </option>
                                                                                    <option value="2">&#11088;&#11088;
                                                                                        Below Average</option>
                                                                                    <option value="1">&#11088; Poor
                                                                                    </option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label
                                                                                    class="form-label">Comment</label>
                                                                                <textarea name="comment"
                                                                                    class="form-control" rows="3"
                                                                                    placeholder="Share your experience..."></textarea>
                                                                            </div>
                                                                            <button type="submit"
                                                                                class="btn btn-primary">Submit
                                                                                Review</button>
                                                                        </form>
                                                                    </div>
                                                                    <% } %>
                                        </div>

                                        <%@ include file="includes/footer.jsp" %>
        </body>

        </html>