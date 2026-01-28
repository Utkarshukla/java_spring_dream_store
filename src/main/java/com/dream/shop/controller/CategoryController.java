package com.dream.shop.controller;

import com.dream.shop.model.Category;
import com.dream.shop.response.ApiResponse;
import com.dream.shop.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    public final ICategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            ApiResponse response = new ApiResponse("Get all categories successfully",
                    categoryService.getAllCategories(), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse( "Internal Server Error: " + e.getMessage(), null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            ApiResponse response = new ApiResponse("Get category by id successfully",
                    categoryService.getCategoryById(id), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse( "Internal Server Error: " + e.getMessage(), null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try {
            ApiResponse response = new ApiResponse("Get category by name successfully",
                    categoryService.getCategoryByName(name), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse( "Internal Server Error: " + e.getMessage(), null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/store")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
        try {
            ApiResponse response = new ApiResponse("Add category successfully",
                    categoryService.addCategory(name), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse( "Internal Server Error: " + e.getMessage(), null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestParam String name, @PathVariable Long id) {
        try {
            ApiResponse response = new ApiResponse("Update category successfully",
                    categoryService.updateCategory(null, id), null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Internal Server Error: " + e.getMessage(), null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            ApiResponse response = new ApiResponse("Delete category successfully", null, null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse("Internal Server Error: " + e.getMessage(), null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


}
