package com.example.ecom_app.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.ecom_app.model.Category;
import com.example.ecom_app.payload.CategoryRequestDTO;
import com.example.ecom_app.payload.CategoryResponse;

public interface CategoryServiceInterface {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryRequestDTO addCategory(CategoryRequestDTO category);
}
