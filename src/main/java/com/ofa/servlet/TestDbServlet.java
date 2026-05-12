package com.ofa.servlet;

import com.util.DbConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

@WebServlet("/test-db")
public class TestDbServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Database Connection Test</h1>");

        try {
            out.println("<p>Attempting to get connection from HikariCP...</p>");
            try (Connection con = DbConnection.getConnection()) {
                if (con != null && !con.isClosed()) {
                    out.println("<p style='color:green'>SUCCESS: Connection obtained!</p>");
                    out.println("<p>Database: " + con.getMetaData().getDatabaseProductVersion() + "</p>");
                } else {
                    out.println("<p style='color:red'>FAILED: Connection is null or closed.</p>");
                }
            }
        } catch (Throwable e) {
            out.println("<p style='color:red'>ERROR: " + e.getMessage() + "</p>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
        }

        out.println("<p><a href='login.jsp'>Back to Login</a></p>");
        out.println("</body></html>");
    }
}
