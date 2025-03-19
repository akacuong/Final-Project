package com.example.finalproject.service;

import com.example.finalproject.dto.BookingDetailDTO;
import com.example.finalproject.entity.*;
import com.example.finalproject.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingDetailService {

    private final BookingDetailRepository bookingDetailRepository;
    private final BookingRepository bookingRepository;
    private final ServiceRepository serviceRepository;
    private final HairstylistRepository hairstylistRepository;

    public BookingDetailService(BookingDetailRepository bookingDetailRepository,
                                BookingRepository bookingRepository,
                                ServiceRepository serviceRepository,
                                HairstylistRepository hairstylistRepository) {
        this.bookingDetailRepository = bookingDetailRepository;
        this.bookingRepository = bookingRepository;
        this.serviceRepository = serviceRepository;
        this.hairstylistRepository = hairstylistRepository;
    }

    public List<BookingDetailDTO> getAllBookingDetails() {
        return bookingDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BookingDetailDTO> getBookingDetailsByBookingId(Integer bookingId) {
        return bookingDetailRepository.findByBooking_BookingId(bookingId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BookingDetailDTO> createBookingDetail(BookingDetailDTO bookingDetailDTO) {
        Booking booking = bookingRepository.findById(bookingDetailDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("❌ Booking không tồn tại!"));

        Hairstylist hairstylist = hairstylistRepository.findById(bookingDetailDTO.getStylistId())
                .orElseThrow(() -> new RuntimeException("❌ Stylist không tồn tại!"));

        // ✅ Tạo danh sách BookingDetail cho từng dịch vụ
        List<BookingDetail> bookingDetails = bookingDetailDTO.getServiceIds().stream().map(serviceId -> {
            ServiceEntity service = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new RuntimeException("❌ Service không tồn tại!"));
            return new BookingDetail(booking, service, hairstylist);
        }).collect(Collectors.toList());

        bookingDetailRepository.saveAll(bookingDetails);
        updateBookingTotalPrice(booking);

        return bookingDetails.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public BookingDetailDTO updateBookingDetail(Integer id, BookingDetailDTO bookingDetailDTO) {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Booking Detail không tồn tại!"));

        // ✅ Cập nhật Service nếu có
        if (bookingDetailDTO.getServiceIds() != null && !bookingDetailDTO.getServiceIds().isEmpty()) {
            ServiceEntity service = serviceRepository.findById(bookingDetailDTO.getServiceIds().get(0)) // ✅ Lấy service đầu tiên từ danh sách
                    .orElseThrow(() -> new RuntimeException("❌ Service không tồn tại!"));
            bookingDetail.setService(service);
        }

        if (bookingDetailDTO.getStylistId() != null) {
            Hairstylist hairstylist = hairstylistRepository.findById(bookingDetailDTO.getStylistId())
                    .orElseThrow(() -> new RuntimeException("❌ Stylist không tồn tại!"));
            bookingDetail.setHairstylist(hairstylist);
        }

        bookingDetailRepository.save(bookingDetail);
        updateBookingTotalPrice(bookingDetail.getBooking());

        return convertToDTO(bookingDetail);
    }

    public void deleteBookingDetail(Integer id) {
        BookingDetail bookingDetail = bookingDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Booking Detail không tồn tại!"));

        Booking booking = bookingDetail.getBooking();
        bookingDetailRepository.deleteById(id);
        updateBookingTotalPrice(booking);
    }

    private void updateBookingTotalPrice(Booking booking) {
        List<BookingDetail> bookingDetails = bookingDetailRepository.findByBooking_BookingId(booking.getBookingId());

        BigDecimal totalPrice = bookingDetails.stream()
                .map(detail -> detail.getService().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        booking.setTotalPrice(totalPrice);
        bookingRepository.save(booking);
    }

    private BookingDetailDTO convertToDTO(BookingDetail bookingDetail) {
        return new BookingDetailDTO(
                bookingDetail.getId(),
                bookingDetail.getBooking().getBookingId(),
                List.of(bookingDetail.getService().getServiceId()), // ✅ Chuyển service thành danh sách
                bookingDetail.getHairstylist().getId()
        );
    }
}
