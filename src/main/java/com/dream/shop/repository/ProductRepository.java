package com.dream.shop.repository;

import com.dream.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brandName);

    List<Product> findByCategoryNameAndBrand(String category, String brandName);

    List<Product> findByName(String productName);

    List<Product> findByBrandAndName(String brandName, String productName);

    Long countByBrandAndName(String brandName, String productName);
}
