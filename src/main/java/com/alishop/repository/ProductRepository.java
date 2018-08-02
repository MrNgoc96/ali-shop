package com.alishop.repository;

import com.alishop.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * author : longoc
 * */
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value = "SELECT p FROM Product p  WHERE p.name like %?1%")
    List<Product> searchProductByName(String name,Pageable pageable);

    @Query(value = "SELECT p FROM Product p inner join p.category c WHERE c.id like ?1")
    List<Product> searchProductByCategory(int categoryId,Pageable pageable);

    @Query(value = "SELECT p FROM Product p inner join p.category c WHERE c.name like '%nam' ")
    List<Product> getMaleProducts(Pageable pageable);

    @Query(value = "SELECT p FROM Product p inner join p.category c WHERE c.name not like '%nam' ")
    List<Product> getFeMaleProducts(Pageable pageable);

    @Query(value = "SELECT p FROM Product p  WHERE p.price BETWEEN ?1 AND ?2")
    List<Product> getProductPrice(double beginPrice, double endPrice, Pageable pageable);

    @Query(value = "SELECT COUNT (p) FROM Product p  WHERE p.price BETWEEN ?1 AND ?2")
    Long countNumberProducts(double beginPrice, double endPrice);


}
