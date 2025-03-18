package com.example.finalproject.repository;

import com.example.finalproject.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> { // ✅ Changed back to Integer
    List<Agent> findByAccount_AccountId(Integer accountId); // ✅ Changed back to Integer
}
