package com.example.ecom_app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ecom_app.model.Category;

public interface CategoryServiceInterface {
    ResponseEntity<List<Category>> getAllCategories();

    ResponseEntity<String> addCategory(Category category);
}
