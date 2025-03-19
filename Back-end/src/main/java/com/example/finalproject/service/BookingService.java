package com.example.finalproject.service;

import com.example.finalproject.dto.BookingDTO;
import com.example.finalproject.entity.Booking;
import com.example.finalproject.entity.Customer;
import com.example.finalproject.entity.Shop;
import com.example.finalproject.repository.BookingRepository;
import com.example.finalproject.repository.CustomerRepository;
import com.example.finalproject.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, ShopRepository shopRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.shopRepository = shopRepository;
    }

    // ✅ Lấy tất cả danh sách booking
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy danh sách booking theo ID
    public BookingDTO getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Booking không tồn tại!"));
        return convertToDTO(booking);
    }

    // ✅ Lấy danh sách booking theo Customer ID
    public List<BookingDTO> getBookingsByCustomerId(Integer customerId) {
        return bookingRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy danh sách booking theo Shop ID (Thêm vào để fix lỗi)
    public List<BookingDTO> getBookingsByShopId(Integer shopId) {
        return bookingRepository.findByShopId(shopId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Tạo booking mới
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Customer customer = customerRepository.findById(bookingDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("❌ Customer không tồn tại!"));

        Shop shop = shopRepository.findById(bookingDTO.getShopId())
                .orElseThrow(() -> new RuntimeException("❌ Shop không tồn tại!"));

        Booking booking = new Booking(customer, shop, bookingDTO.getDatetime(),
                bookingDTO.getStatus(), bookingDTO.getPaymentStatus(), bookingDTO.getTotalPrice());

        return convertToDTO(bookingRepository.save(booking));
    }

    // ✅ Cập nhật booking
    public BookingDTO updateBooking(Integer id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Booking không tồn tại!"));

        if (bookingDTO.getDatetime() != null) booking.setDatetime(bookingDTO.getDatetime());
        if (bookingDTO.getStatus() != null) booking.setStatus(bookingDTO.getStatus());
        if (bookingDTO.getPaymentStatus() != null) booking.setPaymentStatus(bookingDTO.getPaymentStatus());
        if (bookingDTO.getTotalPrice() != null) booking.setTotalPrice(bookingDTO.getTotalPrice());

        return convertToDTO(bookingRepository.save(booking));
    }

    // ✅ Xóa booking
    public void deleteBooking(Integer id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("❌ Booking không tồn tại!");
        }
        bookingRepository.deleteById(id);
    }

    // ✅ Chuyển đổi Entity → DTO
    private BookingDTO convertToDTO(Booking booking) {
        return new BookingDTO(
                booking.getBookingId(),
                booking.getCustomer().getId(),
                booking.getShop().getId(),
                booking.getDatetime(),
                booking.getStatus(),
                booking.getPaymentStatus(),
                booking.getTotalPrice()
        );
    }
}
