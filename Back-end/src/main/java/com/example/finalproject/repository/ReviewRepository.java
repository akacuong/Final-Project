package com.example.finalproject.repository;

import com.example.finalproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByShop_Id(Integer shopId);
}
