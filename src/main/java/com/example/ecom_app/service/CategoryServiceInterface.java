package com.example.ecom_app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ecom_app.model.Category;
import com.example.ecom_app.payload.CategoryResponse;

public interface CategoryServiceInterface {
    CategoryResponse getAllCategories();

    ResponseEntity<String> addCategory(Category category);
}
