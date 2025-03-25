package com.example.finalproject.service;

import com.example.finalproject.dto.PaymentDTO;
import com.example.finalproject.entity.*;
import com.example.finalproject.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final OrderRepository orderRepo;
    private final BookingRepository bookingRepo;
    private final CustomerLoyaltyRepository loyaltyRepo;

    public PaymentService(PaymentRepository paymentRepo,
                          OrderRepository orderRepo,
                          BookingRepository bookingRepo,
                          CustomerLoyaltyRepository loyaltyRepo) {
        this.paymentRepo = paymentRepo;
        this.orderRepo = orderRepo;
        this.bookingRepo = bookingRepo;
        this.loyaltyRepo = loyaltyRepo;
    }

    // ✅ Thanh toán cho ORDER
    @Transactional
    public PaymentDTO payOrder(Integer orderId, String method) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("❌ Order không tồn tại"));

        if ("paid".equalsIgnoreCase(order.getPaymentStatus())) {
            throw new RuntimeException("✅ Đơn hàng đã thanh toán");
        }

        Customer customer = order.getCustomer();
        int totalAmount = order.getTotalPrice().intValue();
        int customerPoints = customer.getPoint() != null ? customer.getPoint() : 0;

        int pointsUsed = 0;
        int amountPaidByPoint = 0;
        int amountPaidByCashOrVnpay = 0;

        if (method.contains("POINT")) {
            pointsUsed = Math.min(totalAmount, customerPoints);
            amountPaidByPoint = pointsUsed;

            if (pointsUsed > 0) {
                customer.setPoint(customerPoints - pointsUsed);

                CustomerLoyalty loyalty = new CustomerLoyalty();
                loyalty.setCustomer(customer);
                loyalty.setConvertPoints(pointsUsed);
                loyalty.setDatetime(LocalDateTime.now());
                loyaltyRepo.save(loyalty);
            }

            if (method.equals("POINT") && amountPaidByPoint < totalAmount) {
                throw new RuntimeException("❌ Không đủ điểm để thanh toán toàn bộ đơn hàng!");
            }

            amountPaidByCashOrVnpay = totalAmount - amountPaidByPoint;
        } else {
            amountPaidByCashOrVnpay = totalAmount;
        }

        order.setPaymentStatus("paid");
        orderRepo.save(order);

        int earnedPoints = totalAmount / 100;
        customer.setPoint((customer.getPoint() != null ? customer.getPoint() : 0) + earnedPoints);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(method);
        payment.setTransactionDate(LocalDateTime.now());

        Payment saved = paymentRepo.save(payment);

        return new PaymentDTO(
                saved.getPaymentId(),
                saved.getPaymentMethod(),
                saved.getTransactionDate(),
                saved.getOrder() != null ? saved.getOrder().getId() : null,
                null,
                pointsUsed,
                amountPaidByPoint,
                amountPaidByCashOrVnpay
        );
    }


    // ✅ Thanh toán cho BOOKING
    @Transactional
    public PaymentDTO payBooking(Integer bookingId, String method) {
        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("❌ Booking không tồn tại"));

        if ("paid".equalsIgnoreCase(booking.getPaymentStatus())) {
            throw new RuntimeException("✅ Booking đã thanh toán");
        }

        Optional<Payment> existing = paymentRepo.findByBooking_BookingId(bookingId);
        if (existing.isPresent()) {
            return convertToDTO(existing.get());
        }

        Customer customer = booking.getCustomer();
        int totalAmount = booking.getTotalPrice().intValue();
        int customerPoints = customer.getPoint() != null ? customer.getPoint() : 0;

        int pointsUsed = 0;
        int amountPaidByPoint = 0;
        int amountPaidByCashOrVnpay = 0;

        if (method.contains("POINT")) {
            pointsUsed = Math.min(totalAmount, customerPoints);
            amountPaidByPoint = pointsUsed;

            if (pointsUsed > 0) {
                customer.setPoint(customerPoints - pointsUsed);

                CustomerLoyalty loyalty = new CustomerLoyalty();
                loyalty.setCustomer(customer);
                loyalty.setConvertPoints(pointsUsed);
                loyalty.setDatetime(LocalDateTime.now());
                loyaltyRepo.save(loyalty);
            }

            if (method.equals("POINT") && amountPaidByPoint < totalAmount) {
                throw new RuntimeException("❌ Không đủ điểm để thanh toán toàn bộ booking!");
            }

            amountPaidByCashOrVnpay = totalAmount - amountPaidByPoint;
        } else {
            amountPaidByCashOrVnpay = totalAmount;
        }

        booking.setPaymentStatus("paid");
        bookingRepo.saveAndFlush(booking);

        int earnedPoints = totalAmount / 100;
        customer.setPoint((customer.getPoint() != null ? customer.getPoint() : 0) + earnedPoints);

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPaymentMethod(method);
        payment.setTransactionDate(LocalDateTime.now());

        Payment saved = paymentRepo.save(payment);

        return new PaymentDTO(
                saved.getPaymentId(),
                saved.getPaymentMethod(),
                saved.getTransactionDate(),
                null,
                saved.getBooking().getBookingId(),
                pointsUsed,
                amountPaidByPoint,
                amountPaidByCashOrVnpay
        );
    }

    // ✅ Convert entity → DTO
    private PaymentDTO convertToDTO(Payment payment) {
        return new PaymentDTO(
                payment.getPaymentId(),
                payment.getPaymentMethod(),
                payment.getTransactionDate(),
                payment.getOrder() != null ? payment.getOrder().getId() : null,
                payment.getBooking() != null ? payment.getBooking().getBookingId() : null,
                0, // pointUsed (nếu cần load lại thì tính toán lại logic)
                0, // amountPaidByPoint
                0  // amountPaidByCashOrVnpay
        );
    }


    // ✅ Lấy theo order
    public Optional<PaymentDTO> getPaymentByOrder(Integer orderId) {
        return paymentRepo.findByOrder_Id(orderId).map(this::convertToDTO);
    }

    // ✅ Lấy theo booking
    public Optional<PaymentDTO> getPaymentByBooking(Integer bookingId) {
        return paymentRepo.findByBooking_BookingId(bookingId).map(this::convertToDTO);
    }

    // ✅ Callback từ VNPay
    @Transactional
    public PaymentDTO handleVnpayCallback(Map<String, String> params) {
        String code = params.get("vnp_ResponseCode");
        String txnRef = params.get("vnp_TxnRef");

        if (!"00".equals(code)) {
            throw new RuntimeException("❌ Giao dịch thất bại từ VNPay");
        }

        txnRef = txnRef.trim();

        if (txnRef.startsWith("ORD_")) {
            Integer orderId = Integer.parseInt(txnRef.replace("ORD_", ""));
            return payOrder(orderId, "POINT+VNPAY");
        } else if (txnRef.startsWith("BOOK_")) {
            Integer bookingId = Integer.parseInt(txnRef.replace("BOOK_", ""));
            return payBooking(bookingId, "POINT+VNPAY");
        } else {
            throw new RuntimeException("❌ Không xác định loại giao dịch");
        }
    }
}