package com.example.finalproject.repository;

import com.example.finalproject.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> { // ✅ Change Integer to Long
    List<Style> findByAgentId(Integer agentId); // ✅ Ensure Long is used
}
