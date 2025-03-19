package com.example.finalproject.repository;

import com.example.finalproject.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomerId(Integer customerId); // ✅ Tìm booking theo Customer ID
    List<Booking> findByShopId(Integer shopId); // ✅ Thêm phương thức này để sửa lỗi
}
