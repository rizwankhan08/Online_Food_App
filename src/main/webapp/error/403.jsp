<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head><meta charset="UTF-8"><title>403 - Access Denied</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
<div style="min-height:100vh;display:flex;align-items:center;justify-content:center;text-align:center">
    <div>
        <div style="font-size:5rem;margin-bottom:16px">&#128683;</div>
        <h1 style="font-size:4rem;font-weight:800;color:var(--warning)">403</h1>
        <h2 style="margin-bottom:8px">Access Denied</h2>
        <p style="color:var(--text-secondary);margin-bottom:24px">You don't have permission to access this page.</p>
        <a href="${pageContext.request.contextPath}/restaurants" class="btn btn-primary btn-lg">&#127829; Go Home</a>
    </div>
</div>
</body>
</html>
