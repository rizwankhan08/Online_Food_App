<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Register - FoodApp</title>
        <link rel="stylesheet" href="css/common.css">
    </head>

    <body>
        <div class="auth-container">
            <div class="auth-card animate-in">
                <div style="text-align:center;font-size:3rem;margin-bottom:10px">&#127829;</div>
                <h2>Create Account</h2>
                <p class="subtitle">Join FoodApp and start ordering</p>

                <% if (request.getAttribute("error") !=null) { %>
                    <div class="alert alert-error">
                        <%= request.getAttribute("error") %>
                    </div>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/registerServlet" method="post" id="registerForm">
                            <div class="form-group">
                                <label class="form-label">Username</label>
                                <input type="text" name="username" class="form-control" placeholder="Choose a username"
                                    required minlength="3">
                            </div>
                            <div class="form-group">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" placeholder="your@email.com"
                                    required>
                            </div>
                            <div class="form-group">
                                <label class="form-label">Phone</label>
                                <input type="tel" name="phone" class="form-control" placeholder="10-digit phone number"
                                    pattern="[0-9]{10}">
                            </div>
                            <div class="form-group">
                                <label class="form-label">Password</label>
                                <input type="password" name="password" class="form-control"
                                    placeholder="Min 6 characters" required minlength="6" id="regPass">
                            </div>
                            <div class="form-group">
                                <label class="form-label">Address</label>
                                <input type="text" name="address" class="form-control"
                                    placeholder="Your delivery address" required>
                            </div>
                            <input type="hidden" name="role" value="customer">
                            <button type="submit" class="btn btn-primary btn-block btn-lg" style="margin-top:8px">Create
                                Account &#127881;</button>
                        </form>

                        <p class="auth-link">
                            Already have an account? <a href="login.jsp">Login</a>
                        </p>
            </div>
        </div>

        <script>
            document.getElementById('registerForm').addEventListener('submit', function (e) {
                var pass = document.getElementById('regPass').value;
                if (pass.length < 6) { e.preventDefault(); alert('Password must be at least 6 characters'); }
            });
        </script>
    </body>

    </html>