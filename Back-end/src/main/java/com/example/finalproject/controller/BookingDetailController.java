package com.example.finalproject.controller;

import com.example.finalproject.dto.BookingDetailDTO;
import com.example.finalproject.service.BookingDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking-details")
public class BookingDetailController {

    private final BookingDetailService bookingDetailService;

    public BookingDetailController(BookingDetailService bookingDetailService) {
        this.bookingDetailService = bookingDetailService;
    }

    // ✅ Lấy tất cả BookingDetails
    @GetMapping
    public ResponseEntity<List<BookingDetailDTO>> getAllBookingDetails() {
        return ResponseEntity.ok(bookingDetailService.getAllBookingDetails());
    }

    // ✅ Lấy BookingDetail theo Booking ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<BookingDetailDTO>> getBookingDetailsByBookingId(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(bookingDetailService.getBookingDetailsByBookingId(bookingId));
    }

    // ✅ Tạo BookingDetail (Có thể thêm nhiều Service cùng lúc)
    @PostMapping
    public ResponseEntity<List<BookingDetailDTO>> createBookingDetail(@RequestBody BookingDetailDTO bookingDetailDTO) {
        return ResponseEntity.status(201).body(bookingDetailService.createBookingDetail(bookingDetailDTO));
    }

    // ✅ Cập nhật BookingDetail (Thay đổi Stylist hoặc Service)
    @PutMapping("/{id}")
    public ResponseEntity<BookingDetailDTO> updateBookingDetail(
            @PathVariable Integer id,
            @RequestBody BookingDetailDTO bookingDetailDTO) {
        return ResponseEntity.ok(bookingDetailService.updateBookingDetail(id, bookingDetailDTO));
    }

    // ✅ Xóa BookingDetail
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookingDetail(@PathVariable Integer id) {
        bookingDetailService.deleteBookingDetail(id);
        return ResponseEntity.ok("✅ Booking Detail đã được xóa!");
    }
}
