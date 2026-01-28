package com.dream.shop.exceptions;

import com.dream.shop.model.Category;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String s) {
        super(s);
    }
}
