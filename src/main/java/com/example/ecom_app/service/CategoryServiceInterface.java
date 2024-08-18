package com.example.ecom_app.service;

import java.util.List;

import com.example.ecom_app.model.Category;

public interface CategoryServiceInterface {
    List<Category> getAllCategories();

    List<Category> addCategory(Category category);
}
