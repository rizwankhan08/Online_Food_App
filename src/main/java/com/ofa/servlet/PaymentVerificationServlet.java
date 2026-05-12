package com.ofa.servlet;

import com.razorpay.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet("/api/verify-payment")
public class PaymentVerificationServlet extends HttpServlet {

    private static final String KEY_SECRET = "bmVjDEr43gdP7cZ9Y1ReVDlI"; // Must match PaymentServlet

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String razorpayOrderId = request.getParameter("razorpay_order_id");
        String razorpayPaymentId = request.getParameter("razorpay_payment_id");
        String razorpaySignature = request.getParameter("razorpay_signature");

        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_payment_id", razorpayPaymentId);
            options.put("razorpay_signature", razorpaySignature);

            boolean isValid = Utils.verifyPaymentSignature(options, KEY_SECRET);

            response.setContentType("application/json");
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status", isValid ? "success" : "failed");
            
            response.getWriter().print(jsonResponse.toString());

        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().print("Verification error: " + e.getMessage());
        }
    }
}
