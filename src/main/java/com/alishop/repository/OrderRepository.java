package com.alishop.repository;

import com.alishop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT o FROM Order o WHERE o.status like :status")
    List<Product> searchProductByStatus(String status);
}
