package com.example.finalproject.service;

import com.example.finalproject.dto.ReviewDTO;
import com.example.finalproject.entity.*;
import com.example.finalproject.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final AgentRepository agentRepository;
    private final ShopRepository shopRepository;

    public ReviewService(ReviewRepository reviewRepository, CustomerRepository customerRepository,
                         AgentRepository agentRepository, ShopRepository shopRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.shopRepository = shopRepository;
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByShopId(Integer shopId) {
        return reviewRepository.findByShop_Id(shopId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Review không tồn tại!"));
        return convertToDTO(review);
    }

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Customer customer = customerRepository.findById(reviewDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("❌ Customer không tồn tại!"));

        Agent agent = agentRepository.findById(reviewDTO.getAgentId())
                .orElseThrow(() -> new RuntimeException("❌ Agent không tồn tại!"));

        Shop shop = shopRepository.findById(reviewDTO.getShopId())
                .orElseThrow(() -> new RuntimeException("❌ Shop không tồn tại!"));

        Review review = new Review(customer, agent, shop, reviewDTO.getRating(),
                reviewDTO.getComment(), LocalDateTime.now());

        return convertToDTO(reviewRepository.save(review));
    }

    @Transactional
    public ReviewDTO updateReview(Integer id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Review không tồn tại!"));

        if (reviewDTO.getRating() != null) review.setRating(reviewDTO.getRating());
        if (reviewDTO.getComment() != null) review.setComment(reviewDTO.getComment());

        return convertToDTO(reviewRepository.save(review));
    }

    public void deleteReview(Integer id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("❌ Review không tồn tại!");
        }
        reviewRepository.deleteById(id);
    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(review.getReviewId(),
                review.getCustomer().getId(),
                review.getAgent().getId(),
                review.getShop().getId(),
                review.getRating(),
                review.getComment(),
                review.getTimestamp());
    }
}
