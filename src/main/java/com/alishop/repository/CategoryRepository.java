package com.alishop.repository;

import com.alishop.entity.Category;
import com.alishop.entity.Order;
import com.alishop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query(value = "SELECT o FROM Order o WHERE o.status like :status")
    List<Product> searchProductByStatus(String status);
}
