package com.example.finalproject.controller;

import com.example.finalproject.dto.BookingDTO;
import com.example.finalproject.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // ✅ Lấy tất cả danh sách booking
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // ✅ Lấy booking theo ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    // ✅ Lấy danh sách booking theo Customer ID
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok(bookingService.getBookingsByCustomerId(customerId));
    }

    // ✅ Lấy danh sách booking theo Shop ID
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByShopId(@PathVariable Integer shopId) {
        return ResponseEntity.ok(bookingService.getBookingsByShopId(shopId));
    }

    // ✅ Tạo mới booking
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.status(201).body(bookingService.createBooking(bookingDTO));
    }

    // ✅ Cập nhật booking
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Integer id, @RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    // ✅ Xóa booking
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok("✅ Booking đã được xóa thành công!");
    }
}
