package com.dream.shop.controller;

import com.dream.shop.model.Product;
import com.dream.shop.request.AddProductRequest;
import com.dream.shop.request.UpdateProductRequest;
import com.dream.shop.response.ApiResponse;
import com.dream.shop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Products fetched successfully", productService.getAllProducts(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to fetch products", null, e.getMessage()));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Product fetched successfully", productService.getProductById(productId), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to fetch product", null, e.getMessage()));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody AddProductRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Product created successfully", productService.addProduct(request), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to create product", null, e.getMessage()));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody Product request) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Product updated successfully", productService.updateProduct(productId, request), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to update product", null, e.getMessage()));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("Product deleted successfully", null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Failed to delete product", null, e.getMessage()));
        }
    }
}
