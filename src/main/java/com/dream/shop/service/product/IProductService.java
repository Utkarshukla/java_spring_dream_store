package com.dream.shop.service.product;

import com.dream.shop.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product getProductById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brandName);
    List<Product> getProductsByCategoryAndBrand(String category, String brandName);
    List<Product> getProductsByProductName(String productName);
    List<Product> getProductsByBrandAndProductName(String brandName, String productName);
    Long countProductsByBrandAndName(String brandName, String productName);

}
