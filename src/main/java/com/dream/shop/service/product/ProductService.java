package com.dream.shop.service.product;

import com.dream.shop.exceptions.ProductNotFoundException;
import com.dream.shop.model.Category;
import com.dream.shop.model.Product;
import com.dream.shop.repository.CategoryRepository;
import com.dream.shop.repository.ProductRepository;
import com.dream.shop.request.AddProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Product addProduct(AddProductRequest product) {
        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName())).orElseGet(()-> categoryRepository.save(new Category(null, product.getCategory().getName(), null)));
        product.setCategory(category);
        return productRepository.save(createProduct(product,category));
//        return ;
    }

    private Product createProduct(AddProductRequest product, Category category){
        return new Product(
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getInventory(),
                product.getDescription(),
                category
        );

    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName())).orElseGet(()-> categoryRepository.save(new Category(null, product.getCategory().getName(), null)));
        product.setCategory(category);
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            return updateExistingProduct(existingProduct.get(), product);
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

    private Product updateExistingProduct(Product existingProduct, Product newProductData) {
        existingProduct.setName(newProductData.getName());
        existingProduct.setBrand(newProductData.getBrand());
        existingProduct.setPrice(newProductData.getPrice());
        existingProduct.setInventory(newProductData.getInventory());
        existingProduct.setDescription(newProductData.getDescription());
        existingProduct.setCategory(newProductData.getCategory());
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {throw new ProductNotFoundException("Product not found with id: " + id);});
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brandName) {
        return productRepository.findByBrand(brandName);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brandName) {
        return productRepository.findByCategoryNameAndBrand(category, brandName);
    }

    @Override
    public List<Product> getProductsByProductName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public List<Product> getProductsByBrandAndProductName(String brandName, String productName) {
        return productRepository.findByBrandAndName(brandName, productName);
    }

    @Override
    public Long countProductsByBrandAndName(String brandName, String productName) {
        return productRepository.countByBrandAndName(brandName, productName);
    }
}
