package com.filter;

import com.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authentication filter to protect pages that require login.
 * Allows public pages (login, register, static resources) to pass through.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        // Allow public resources
        if (isPublicResource(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Check session
        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        // Admin route protection
        if (path.startsWith("/admin") && !user.isAdmin()) {
            resp.sendRedirect(contextPath + "/restaurants");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicResource(String path) {
        return path.equals("/") ||
               path.equals("/login.jsp") || path.equals("/login.html") ||
               path.equals("/register.jsp") || path.equals("/register.html") ||
               path.equals("/LoginServlet") || path.equals("/registerServlet") ||
               path.startsWith("/css/") || path.startsWith("/js/") ||
               path.startsWith("/images/") || path.startsWith("/fonts/") ||
               path.endsWith(".css") || path.endsWith(".js") ||
               path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg") ||
               path.endsWith(".gif") || path.endsWith(".ico") || path.endsWith(".svg") ||
               path.startsWith("/api/") || path.equals("/test-db");
    }
}
