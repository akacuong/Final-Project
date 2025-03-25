package com.example.finalproject.service;

import com.example.finalproject.dto.BookingDetailDTO;
import com.example.finalproject.dto.OrderDetailDTO;
import com.example.finalproject.entity.Order;
import com.example.finalproject.entity.OrderDetail;
import com.example.finalproject.entity.Product;
import com.example.finalproject.repository.OrderDetailRepository;
import com.example.finalproject.repository.OrderRepository;
import com.example.finalproject.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    /**
     * ✅ Thêm OrderDetail vào Order và cập nhật tổng tiền
     */
    @Transactional
    public OrderDetailDTO addOrderDetail(Integer orderId, Integer productId, Integer quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("❌ Order không tồn tại!"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("❌ Product không tồn tại!"));

        OrderDetail orderDetail = new OrderDetail(order, product, quantity);

        // ✅ Gắn vào danh sách orderDetails của order
        order.getOrderDetails().add(orderDetail);

        // ✅ Lưu orderDetail trước
        orderDetail = orderDetailRepository.save(orderDetail);

        // ✅ Sau đó tính lại tổng tiền
        updateOrderTotalPrice(order);

        return convertToDTO(orderDetail);
    }


    /**
     * ✅ Cập nhật số lượng OrderDetail
     */
    @Transactional
    public OrderDetailDTO updateOrderDetail(Integer id, Integer quantity) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ OrderDetail không tồn tại!"));

        orderDetail.setQuantity(quantity);
        orderDetail = orderDetailRepository.save(orderDetail);

        // ✅ Cập nhật lại tổng giá trị đơn hàng
        updateOrderTotalPrice(orderDetail.getOrder());

        return convertToDTO(orderDetail);
    }

    /**
     * ✅ Lấy tất cả OrderDetail
     */
    public List<OrderDetailDTO> getAllOrderDetails() {
        return orderDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * ✅ Lấy danh sách OrderDetail theo Order ID
     */
    public List<OrderDetailDTO> getOrderDetailsByOrderId(Integer orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_Id(orderId);
        return orderDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    /**
     * ✅ Xóa OrderDetail và cập nhật tổng tiền
     */
    @Transactional
    public void deleteOrderDetail(Integer id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ OrderDetail không tồn tại!"));

        Order order = orderDetail.getOrder();

        // ✅ Xóa khỏi danh sách order.getOrderDetails()
        order.getOrderDetails().removeIf(od -> od.getId().equals(id));

        // ✅ Xóa khỏi repository
        orderDetailRepository.delete(orderDetail);

        // ✅ Cập nhật lại giá đơn
        updateOrderTotalPrice(order);
    }
    /**
     * ✅ Hàm cập nhật tổng giá trị đơn hàng dựa trên các OrderDetail
     */
    private void updateOrderTotalPrice(Order order) {
        if (order == null || order.getOrderDetails() == null) return;

        BigDecimal newTotalPrice = order.getOrderDetails().stream()
                .map(detail -> detail.getProduct().getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!order.getTotalPrice().equals(newTotalPrice)) { // Chỉ cập nhật nếu có thay đổi
            order.setTotalPrice(newTotalPrice);
            orderRepository.save(order);
        }
    }

    /**
     * ✅ Chuyển đổi OrderDetail sang OrderDetailDTO
     */
    private OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        return new OrderDetailDTO(
                orderDetail.getId(),
                orderDetail.getOrder().getId(),
                orderDetail.getProduct().getId(),
                orderDetail.getQuantity(),
                orderDetail.getProduct().getPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()))
        );
    }
}
