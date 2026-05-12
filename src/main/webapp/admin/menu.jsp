<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.Menu, com.model.Restaurant" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin - Menu</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/admin.css">
</head>
<body>
<%@ include file="/includes/navbar.jsp" %>
<div class="admin-layout">
<%@ include file="sidebar.jsp" %>
<main class="admin-main">
    <div class="admin-header">
        <h1>&#127860; Menu Items</h1>
        <button class="btn btn-primary" onclick="document.getElementById('addModal').classList.add('show')">&#43; Add Item</button>
    </div>

    <!-- Restaurant Filter -->
    <div class="card" style="padding:16px;margin-bottom:16px">
        <form action="<%= ctx %>/admin/menu" method="get" style="display:flex;gap:10px;align-items:center">
            <select name="restaurantId" class="form-control" style="max-width:280px">
                <option value="">-- Select Restaurant --</option>
                <% List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
                   Restaurant selectedR = (Restaurant) request.getAttribute("selectedRestaurant");
                   if (restaurants != null) { for (Restaurant r : restaurants) { %>
                <option value="<%= r.getId() %>" <%= (selectedR != null && selectedR.getId() == r.getId()) ? "selected" : "" %>><%= r.getName() %></option>
                <% } } %>
            </select>
            <button type="submit" class="btn btn-outline">Filter</button>
        </form>
    </div>

    <% List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
       if (menuList != null && !menuList.isEmpty()) { %>
    <div class="table-container">
        <table>
            <thead><tr><th>ID</th><th>Image</th><th>Name</th><th>Category</th><th>Price</th><th>Rating</th><th>Type</th><th>Available</th><th>Actions</th></tr></thead>
            <tbody>
                <% for (Menu m : menuList) { %>
                <tr>
                    <td>#<%= m.getId() %></td>
                    <td><img src="<%= ctx %>/<%= m.getImageUrl() %>" style="width:50px;height:40px;object-fit:cover;border-radius:6px" alt=""></td>
                    <td><strong><%= m.getName() %></strong><br><span style="color:var(--text-muted);font-size:0.75rem"><%= m.getDescription() != null && m.getDescription().length() > 40 ? m.getDescription().substring(0,40)+"..." : m.getDescription() %></span></td>
                    <td><span class="badge badge-info"><%= m.getCategory() %></span></td>
                    <td class="price">&#8377;<%= String.format("%.0f", m.getPrice()) %></td>
                    <td>&#11088; <%= m.getRating() %></td>
                    <td><span class="badge <%= m.isVeg() ? "badge-veg" : "badge-nonveg" %>"><%= m.isVeg() ? "Veg" : "Non-Veg" %></span></td>
                    <td><span class="badge <%= m.isAvailable() ? "badge-success" : "badge-danger" %>"><%= m.isAvailable() ? "Yes" : "No" %></span></td>
                    <td>
                        <button class="btn btn-sm btn-outline" onclick="editMenu(<%= m.getId() %>,<%= m.getRestaurantId() %>,'<%= m.getName().replace("'","") %>','<%= m.getDescription() != null ? m.getDescription().replace("'","") : "" %>','<%= m.getPrice() %>','<%= m.getCategory() %>','<%= m.getRating() %>','<%= m.isVeg() %>','<%= m.isAvailable() %>','<%= m.getImageUrl() %>')">Edit</button>
                        <form action="<%= ctx %>/admin/menu/delete" method="post" style="display:inline" onsubmit="return confirm('Delete?')">
                            <input type="hidden" name="id" value="<%= m.getId() %>">
                            <input type="hidden" name="restaurantId" value="<%= m.getRestaurantId() %>">
                            <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                        </form>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
    <% } else { %>
    <div class="empty-state"><div class="icon">&#127860;</div><p>Select a restaurant to view menu items</p></div>
    <% } %>
</main>
</div>

<!-- Add Modal -->
<div class="modal-overlay" id="addModal">
    <div class="modal">
        <div class="modal-header"><h3>Add Menu Item</h3><button class="modal-close" onclick="document.getElementById('addModal').classList.remove('show')">&times;</button></div>
        <form action="<%= ctx %>/admin/menu/add" method="post">
            <div class="form-group"><label class="form-label">Restaurant</label>
                <select name="restaurantId" class="form-control" required>
                    <% if (restaurants != null) { for (Restaurant r : restaurants) { %>
                    <option value="<%= r.getId() %>"><%= r.getName() %></option>
                    <% } } %>
                </select>
            </div>
            <div class="form-group"><label class="form-label">Item Name</label><input type="text" name="name" class="form-control" required></div>
            <div class="form-group"><label class="form-label">Description</label><textarea name="description" class="form-control" rows="2"></textarea></div>
            <div style="display:grid;grid-template-columns:1fr 1fr;gap:10px">
                <div class="form-group"><label class="form-label">Price (₹)</label><input type="number" name="price" class="form-control" step="0.01" required></div>
                <div class="form-group"><label class="form-label">Rating</label><input type="number" name="rating" class="form-control" step="0.1" min="0" max="5" value="4.0"></div>
            </div>
            <div class="form-group"><label class="form-label">Category</label><input type="text" name="category" class="form-control" placeholder="e.g. Pizza, Burger"></div>
            <div class="form-group"><label class="form-label">Image URL</label><input type="text" name="imageUrl" class="form-control"></div>
            <div class="form-group"><label style="display:flex;align-items:center;gap:8px;cursor:pointer"><input type="checkbox" name="isVeg" checked> Vegetarian</label></div>
            <button type="submit" class="btn btn-primary btn-block">Add Item</button>
        </form>
    </div>
</div>

<!-- Edit Modal -->
<div class="modal-overlay" id="editModal">
    <div class="modal">
        <div class="modal-header"><h3>Edit Menu Item</h3><button class="modal-close" onclick="document.getElementById('editModal').classList.remove('show')">&times;</button></div>
        <form action="<%= ctx %>/admin/menu/update" method="post">
            <input type="hidden" name="id" id="eId"><input type="hidden" name="restaurantId" id="eRid">
            <div class="form-group"><label class="form-label">Name</label><input type="text" name="name" id="eName" class="form-control" required></div>
            <div class="form-group"><label class="form-label">Description</label><textarea name="description" id="eDesc" class="form-control" rows="2"></textarea></div>
            <div style="display:grid;grid-template-columns:1fr 1fr;gap:10px">
                <div class="form-group"><label class="form-label">Price</label><input type="number" name="price" id="ePrice" class="form-control" step="0.01"></div>
                <div class="form-group"><label class="form-label">Rating</label><input type="number" name="rating" id="eRating" class="form-control" step="0.1" min="0" max="5"></div>
            </div>
            <div class="form-group"><label class="form-label">Category</label><input type="text" name="category" id="eCat" class="form-control"></div>
            <div class="form-group"><label class="form-label">Image URL</label><input type="text" name="imageUrl" id="eImg" class="form-control"></div>
            <div style="display:flex;gap:20px">
                <label style="display:flex;align-items:center;gap:8px;cursor:pointer"><input type="checkbox" name="isVeg" id="eVeg"> Veg</label>
                <label style="display:flex;align-items:center;gap:8px;cursor:pointer"><input type="checkbox" name="isAvailable" id="eAvail"> Available</label>
            </div>
            <button type="submit" class="btn btn-primary btn-block" style="margin-top:14px">Update Item</button>
        </form>
    </div>
</div>
<script>
function editMenu(id,rid,name,desc,price,cat,rating,veg,avail,img){
    document.getElementById('eId').value=id; document.getElementById('eRid').value=rid;
    document.getElementById('eName').value=name; document.getElementById('eDesc').value=desc;
    document.getElementById('ePrice').value=price; document.getElementById('eCat').value=cat;
    document.getElementById('eRating').value=rating; document.getElementById('eImg').value=img;
    document.getElementById('eVeg').checked=(veg==='true'); document.getElementById('eAvail').checked=(avail==='true');
    document.getElementById('editModal').classList.add('show');
}
</script>
</body>
</html>
