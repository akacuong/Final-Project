package com.example.finalproject.service;

import com.example.finalproject.dto.OrderDTO;
import com.example.finalproject.entity.Customer;
import com.example.finalproject.entity.Order;
import com.example.finalproject.repository.CustomerRepository;
import com.example.finalproject.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Order không tồn tại!"));
        return convertToDTO(order);
    }

    public List<OrderDTO> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("❌ Customer không tồn tại!"));

        Order order = new Order(customer, "pending");
        return convertToDTO(orderRepository.save(order));
    }

    @Transactional
    public OrderDTO updateOrder(Integer id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Order không tồn tại!"));

        if (orderDTO.getStatus() != null) order.setStatus(orderDTO.getStatus());

        return convertToDTO(orderRepository.save(order));
    }

    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("❌ Order không tồn tại!");
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getCreatedAt()
        );
    }
}
