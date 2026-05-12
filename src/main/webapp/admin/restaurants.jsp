<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Restaurant" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin - Restaurants</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
</head>
<body>
<%@ include file="/includes/navbar.jsp" %>
<div class="admin-layout">
<%@ include file="sidebar.jsp" %>
<main class="admin-main">
    <div class="admin-header">
        <h1>&#127869; Restaurants</h1>
        <button class="btn btn-primary" onclick="document.getElementById('addModal').classList.add('show')">&#43; Add Restaurant</button>
    </div>

    <div class="table-container">
        <table>
            <thead><tr><th>ID</th><th>Image</th><th>Name</th><th>Location</th><th>Cuisine</th><th>Rating</th><th>Delivery</th><th>Status</th><th>Actions</th></tr></thead>
            <tbody>
                <% List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
                   if (restaurants != null) { for (Restaurant r : restaurants) { %>
                <tr>
                    <td>#<%= r.getId() %></td>
                    <td><img src="<%= ctx %>/<%= r.getImageUrl() %>" style="width:50px;height:40px;object-fit:cover;border-radius:6px" alt=""></td>
                    <td><strong><%= r.getName() %></strong></td>
                    <td style="color:var(--text-secondary)"><%= r.getLocation() %></td>
                    <td><span class="badge badge-primary"><%= r.getCuisineType() %></span></td>
                    <td><span style="color:var(--success)">&#11088; <%= r.getRating() %></span></td>
                    <td><%= r.getDeliveryTime() %></td>
                    <td><span class="badge <%= r.isActive() ? "badge-success" : "badge-danger" %>"><%= r.isActive() ? "Active" : "Inactive" %></span></td>
                    <td>
                        <button class="btn btn-sm btn-outline" onclick="editRestaurant(<%= r.getId() %>,'<%= r.getName().replace("'","") %>','<%= r.getDescription() != null ? r.getDescription().replace("'","") : "" %>','<%= r.getLocation() %>','<%= r.getCuisineType() %>','<%= r.getRating() %>','<%= r.getDeliveryTime() %>','<%= r.getMinOrder() %>','<%= r.getImageUrl() %>','<%= r.isActive() %>')">Edit</button>
                        <form action="<%= ctx %>/admin/restaurant/delete" method="post" style="display:inline" onsubmit="return confirm('Delete this restaurant?')">
                            <input type="hidden" name="id" value="<%= r.getId() %>">
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
                <% } } %>
            </tbody>
        </table>
    </div>
</main>
</div>

<!-- Add Modal -->
<div class="modal-overlay" id="addModal">
    <div class="modal">
        <div class="modal-header"><h3>Add Restaurant</h3><button class="modal-close" onclick="document.getElementById('addModal').classList.remove('show')">&times;</button></div>
        <form action="<%= ctx %>/admin/restaurant/add" method="post">
            <div class="form-group"><label class="form-label">Name</label><input type="text" name="name" class="form-control" required></div>
            <div class="form-group"><label class="form-label">Description</label><textarea name="description" class="form-control" rows="2"></textarea></div>
            <div class="form-group"><label class="form-label">Location</label><input type="text" name="location" class="form-control" required></div>
            <div class="form-group"><label class="form-label">Cuisine Type</label><input type="text" name="cuisineType" class="form-control"></div>
            <div style="display:grid;grid-template-columns:1fr 1fr;gap:10px">
                <div class="form-group"><label class="form-label">Rating</label><input type="number" name="rating" class="form-control" step="0.1" min="0" max="5" value="4.0"></div>
                <div class="form-group"><label class="form-label">Min Order (₹)</label><input type="number" name="minOrder" class="form-control" value="99"></div>
            </div>
            <div class="form-group"><label class="form-label">Delivery Time</label><input type="text" name="deliveryTime" class="form-control" placeholder="e.g. 25-35 min"></div>
            <div class="form-group"><label class="form-label">Image URL</label><input type="text" name="imageUrl" class="form-control" placeholder="images/pizza.jpg"></div>
            <button type="submit" class="btn btn-primary btn-block">Add Restaurant</button>
        </form>
    </div>
</div>

<!-- Edit Modal -->
<div class="modal-overlay" id="editModal">
    <div class="modal">
        <div class="modal-header"><h3>Edit Restaurant</h3><button class="modal-close" onclick="document.getElementById('editModal').classList.remove('show')">&times;</button></div>
        <form action="<%= ctx %>/admin/restaurant/update" method="post">
            <input type="hidden" name="id" id="editId">
            <div class="form-group"><label class="form-label">Name</label><input type="text" name="name" id="editName" class="form-control" required></div>
            <div class="form-group"><label class="form-label">Description</label><textarea name="description" id="editDesc" class="form-control" rows="2"></textarea></div>
            <div class="form-group"><label class="form-label">Location</label><input type="text" name="location" id="editLoc" class="form-control"></div>
            <div class="form-group"><label class="form-label">Cuisine Type</label><input type="text" name="cuisineType" id="editCuisine" class="form-control"></div>
            <div style="display:grid;grid-template-columns:1fr 1fr;gap:10px">
                <div class="form-group"><label class="form-label">Rating</label><input type="number" name="rating" id="editRating" class="form-control" step="0.1" min="0" max="5"></div>
                <div class="form-group"><label class="form-label">Min Order (₹)</label><input type="number" name="minOrder" id="editMinOrder" class="form-control"></div>
            </div>
            <div class="form-group"><label class="form-label">Delivery Time</label><input type="text" name="deliveryTime" id="editDelivery" class="form-control"></div>
            <div class="form-group"><label class="form-label">Image URL</label><input type="text" name="imageUrl" id="editImage" class="form-control"></div>
            <div class="form-group"><label style="display:flex;align-items:center;gap:8px;cursor:pointer"><input type="checkbox" name="isActive" id="editActive"> Active</label></div>
            <button type="submit" class="btn btn-primary btn-block">Update Restaurant</button>
        </form>
    </div>
</div>
<script>
function editRestaurant(id,name,desc,loc,cuisine,rating,delivery,minOrder,img,active){
    document.getElementById('editId').value=id;
    document.getElementById('editName').value=name;
    document.getElementById('editDesc').value=desc;
    document.getElementById('editLoc').value=loc;
    document.getElementById('editCuisine').value=cuisine;
    document.getElementById('editRating').value=rating;
    document.getElementById('editDelivery').value=delivery;
    document.getElementById('editMinOrder').value=minOrder;
    document.getElementById('editImage').value=img;
    document.getElementById('editActive').checked=(active==='true');
    document.getElementById('editModal').classList.add('show');
}
</script>
</body>
</html>
