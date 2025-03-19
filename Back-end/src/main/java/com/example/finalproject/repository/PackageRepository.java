package com.example.finalproject.repository;

import com.example.finalproject.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
    List<Package> findByAgent_Id(Integer agentId);
}
