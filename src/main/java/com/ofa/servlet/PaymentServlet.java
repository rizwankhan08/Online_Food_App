package com.ofa.servlet;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet("/api/create-order")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // IMPORTANT: Replace these with your actual Razorpay Test Keys from Dashboard
    private static final String KEY_ID = "rzp_test_SoTVK9gkvu3UEe";
    private static final String KEY_SECRET = "bmVjDEr43gdP7cZ9Y1ReVDlI";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String amountStr = request.getParameter("amount");
        if (amountStr == null) {
            response.setStatus(400);
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            // Razorpay accepts amount in paise (1 INR = 100 paise)
            int amountInPaise = (int) (amount * 100);

            RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amountInPaise);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = razorpay.orders.create(orderRequest);

            response.setContentType("application/json");
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("id", (Object) order.get("id"));
            jsonResponse.put("amount", (Object) order.get("amount"));
            jsonResponse.put("key", (Object) KEY_ID);
            
            response.getWriter().print(jsonResponse.toString());

        } catch (RazorpayException e) {
            response.setStatus(500);
            response.getWriter().print("Error creating Razorpay order: " + e.getMessage());
        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.getWriter().print("Invalid amount format");
        }
    }
}
