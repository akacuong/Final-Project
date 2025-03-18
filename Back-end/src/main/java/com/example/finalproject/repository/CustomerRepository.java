package com.example.finalproject.repository;

import com.example.finalproject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    // 🔥 Cách đúng: Tìm tất cả Customers theo username của Account
    List<Customer> findByAccount_Username(String username);
}
