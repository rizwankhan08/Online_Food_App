<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.model.*, com.model.Cart, com.model.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Checkout - FoodApp</title>
<link rel="stylesheet" href="css/common.css">
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
</head>
<body>
<%@ include file="includes/navbar.jsp" %>
<%
    Cart checkCart = (Cart) session.getAttribute("cart");
    List<Address> addresses = (List<Address>) request.getAttribute("addresses");
    if (checkCart == null || checkCart.getItems().isEmpty()) {
        response.sendRedirect(ctx + "/cart.jsp"); return;
    }
    double subtotal = checkCart.getTotalAmount();
    double delivery = 30.0;
    double tax = Math.round(subtotal * 0.05 * 100.0) / 100.0;
    double grand = subtotal + delivery + tax;
%>
<div class="container" style="max-width:900px">
    <div class="section-header">
        <h2 class="section-title">&#128666; Checkout</h2>
    </div>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form action="<%= ctx %>/checkout" method="post" id="checkoutForm">
        <input type="hidden" name="razorpay_payment_id" id="razorpay_payment_id">
        <input type="hidden" name="razorpay_order_id" id="razorpay_order_id">
        <input type="hidden" name="razorpay_signature" id="razorpay_signature">
        <div style="display:grid;grid-template-columns:1.5fr 1fr;gap:20px">
            <div>
                <!-- Delivery Address -->
                <div class="card" style="padding:20px;margin-bottom:16px">
                    <h3 style="margin-bottom:14px">&#128205; Delivery Address</h3>
                    <% if (addresses != null && !addresses.isEmpty()) {
                        for (Address addr : addresses) { %>
                        <label style="display:flex;align-items:flex-start;gap:10px;padding:12px;border:1px solid var(--border);border-radius:var(--radius-sm);margin-bottom:8px;cursor:pointer">
                            <input type="radio" name="deliveryAddress" value="<%= addr.getFullAddress() %>" style="margin-top:3px" <%= addr.isDefault() ? "checked" : "" %>>
                            <span style="display:block">
                                <strong><%= addr.getLabel() %></strong>
                                <% if (addr.isDefault()) { %><span class="badge badge-success" style="margin-left:6px;font-size:0.7rem">Default</span><% } %>
                                <span style="display:block;color:var(--text-secondary);font-size:0.85rem;margin-top:2px"><%= addr.getFullAddress() %></span>
                            </span>
                        </label>
                    <% } %>
                    <% } %>
                    <div class="form-group" style="margin-top:10px">
                        <label class="form-label">Or enter a new address</label>
                        <input type="text" name="deliveryAddress" class="form-control" placeholder="Enter delivery address" value="<%= navUser != null ? navUser.getAddress() : "" %>">
                    </div>
                </div>
                <!-- Payment Method -->
                <div class="card" style="padding:20px;margin-bottom:16px">
                    <h3 style="margin-bottom:14px">&#128179; Payment Method</h3>
                    <label style="display:flex;align-items:center;gap:10px;padding:12px;border:1px solid var(--border);border-radius:var(--radius-sm);margin-bottom:8px;cursor:pointer">
                        <input type="radio" name="paymentMethod" value="cod" checked>
                        <span>&#128181; Cash on Delivery</span>
                    </label>
                    <label style="display:flex;align-items:center;gap:10px;padding:12px;border:1px solid var(--border);border-radius:var(--radius-sm);margin-bottom:8px;cursor:pointer">
                        <input type="radio" name="paymentMethod" value="upi">
                        <span>&#128247; UPI Payment</span>
                    </label>
                    <label style="display:flex;align-items:center;gap:10px;padding:12px;border:1px solid var(--border);border-radius:var(--radius-sm);cursor:pointer">
                        <input type="radio" name="paymentMethod" value="card">
                        <span>&#128179; Credit / Debit Card</span>
                    </label>
                </div>
                <!-- Special Instructions -->
                <div class="card" style="padding:20px">
                    <h3 style="margin-bottom:12px">&#128221; Special Instructions</h3>
                    <textarea name="specialInstructions" class="form-control" rows="3" placeholder="Any special request?"></textarea>
                </div>
            </div>
            <!-- Order Summary -->
            <div>
                <div class="card" style="padding:20px;position:sticky;top:80px">
                    <h3 style="margin-bottom:16px">Order Summary</h3>
                    <% for (CartItem ci : checkCart.getItems().values()) { %>
                    <div style="display:flex;justify-content:space-between;margin-bottom:8px;font-size:0.9rem">
                        <span style="color:var(--text-secondary)"><%= ci.getName() %> x<%= ci.getQuantity() %></span>
                        <span>&#8377;<%= String.format("%.0f", ci.getTotalPrice()) %></span>
                    </div>
                    <% } %>
                    <div style="border-top:1px solid var(--border);margin:12px 0;padding-top:12px">
                        <div style="display:flex;justify-content:space-between;font-size:0.9rem;margin-bottom:6px">
                            <span style="color:var(--text-secondary)">Subtotal</span><span>&#8377;<%= String.format("%.2f", subtotal) %></span>
                        </div>
                        <div style="display:flex;justify-content:space-between;font-size:0.9rem;margin-bottom:6px">
                            <span style="color:var(--text-secondary)">Delivery</span><span>&#8377;<%= String.format("%.2f", delivery) %></span>
                        </div>
                        <div style="display:flex;justify-content:space-between;font-size:0.9rem;margin-bottom:6px">
                            <span style="color:var(--text-secondary)">GST (5%)</span><span>&#8377;<%= String.format("%.2f", tax) %></span>
                        </div>
                        <div style="display:flex;justify-content:space-between;font-weight:700;font-size:1.1rem;margin-top:8px;padding-top:8px;border-top:1px solid var(--border)">
                            <span>Total</span><span class="price">&#8377;<%= String.format("%.2f", grand) %></span>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block btn-lg" style="margin-top:12px">
                        &#127381; Place Order
                    </button>
                    <a href="<%= ctx %>/cart.jsp" class="btn btn-outline btn-block" style="margin-top:8px">Back to Cart</a>
                </div>
            </div>
        </div>
    </form>
</div>
<script>
document.getElementById('checkoutForm').addEventListener('submit', function(e) {
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    
    // If COD, just submit the form
    if (paymentMethod === 'cod') {
        return true;
    }

    // For UPI or Card, handle via Razorpay
    e.preventDefault();
    
    const amount = parseFloat("<%= grand %>");
    
    // 1. Create order on server
    fetch('<%= ctx %>/api/create-order?amount=' + amount, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        const options = {
            "key": data.key,
            "amount": data.amount,
            "currency": "INR",
            "name": "FoodApp",
            "description": "Order Payment",
            "order_id": data.id,
            "handler": function (response){
                // 2. On success, set hidden fields and submit form
                document.getElementById('razorpay_payment_id').value = response.razorpay_payment_id;
                document.getElementById('razorpay_order_id').value = response.razorpay_order_id;
                document.getElementById('razorpay_signature').value = response.razorpay_signature;
                
                // Submit the form to place the order in our DB
                document.getElementById('checkoutForm').submit();
            },
            "prefill": {
                "name": "<%= navUser.getUsername() %>",
                "email": "<%= navUser.getEmail() %>",
                "contact": "<%= navUser.getPhone() != null ? navUser.getPhone() : "" %>"
            },
            "theme": {
                "color": "#ff5722"
            }
        };
        const rzp = new Razorpay(options);
        rzp.open();
    })
    .catch(error => {
        alert('Error initiating payment. Please try again.');
        console.error('Error:', error);
    });
});
</script>
<%@ include file="includes/footer.jsp" %>
</body>
</html>
