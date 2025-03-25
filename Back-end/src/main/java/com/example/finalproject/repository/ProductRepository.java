package com.example.finalproject.repository;

import com.example.finalproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory_Id(Integer categoryId);
}
