package com.example.finalproject.controller;

import com.example.finalproject.dto.PaymentDTO;
import com.example.finalproject.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ✅ Thanh toán cho Order
    @PostMapping("/order/{orderId}")
    public ResponseEntity<?> payOrder(
            @PathVariable Integer orderId,
            @RequestParam(defaultValue = "CASH") String method) {
        try {
            // 🔥 Chuẩn hóa method trước khi gọi service
            method = method.trim().toUpperCase();

            PaymentDTO dto = paymentService.payOrder(orderId, method);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("❌ Lỗi: " + e.getMessage());
        }
    }

    // ✅ Thanh toán cho Booking
    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<?> payBooking(
            @PathVariable Integer bookingId,
            @RequestParam(defaultValue = "CASH") String method) {
        try {
            // 🔥 Chuẩn hóa method trước khi gọi service
            method = method.trim().toUpperCase();

            PaymentDTO dto = paymentService.payBooking(bookingId, method);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("❌ Lỗi: " + e.getMessage());
        }
    }

    // ✅ Lấy thông tin thanh toán của Order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getPaymentByOrder(@PathVariable Integer orderId) {
        Optional<PaymentDTO> dto = paymentService.getPaymentByOrder(orderId);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Lấy thông tin thanh toán của Booking
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getPaymentByBooking(@PathVariable Integer bookingId) {
        Optional<PaymentDTO> dto = paymentService.getPaymentByBooking(bookingId);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Xử lý callback từ VNPay
    @PostMapping("/vnpay/callback")
    public ResponseEntity<?> handleVnpayCallback(@RequestParam Map<String, String> params) {
        try {
            String code = params.get("vnp_ResponseCode");
            String txnRef = params.get("vnp_TxnRef");

            if (!"00".equals(code)) {
                throw new RuntimeException("❌ Giao dịch thất bại từ VNPay");
            }

            PaymentDTO dto;

            if (txnRef.startsWith("ORD_")) {
                Integer orderId = Integer.parseInt(txnRef.replace("ORD_", ""));
                dto = paymentService.payOrder(orderId, "VNPAY");
            } else if (txnRef.startsWith("BOOK_")) {
                Integer bookingId = Integer.parseInt(txnRef.replace("BOOK_", ""));
                dto = paymentService.payBooking(bookingId, "VNPAY");
            } else {
                throw new RuntimeException("❌ Không xác định loại giao dịch (Order/Booking)");
            }

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Thanh toán thất bại: " + e.getMessage());
        }
    }
}
