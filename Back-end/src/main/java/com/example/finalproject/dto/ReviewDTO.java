package com.example.finalproject.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Integer reviewId;
    private Integer customerId;
    private Integer agentId;
    private Integer shopId;
    private Integer rating;
    private String comment;
    private LocalDateTime timestamp;

    public ReviewDTO() {}

    public ReviewDTO(Integer reviewId, Integer customerId, Integer agentId, Integer shopId, Integer rating, String comment, LocalDateTime timestamp) {
        this.reviewId = reviewId;
        this.customerId = customerId;
        this.agentId = agentId;
        this.shopId = shopId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
