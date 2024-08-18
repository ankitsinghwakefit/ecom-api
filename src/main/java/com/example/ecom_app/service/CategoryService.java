package com.example.ecom_app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecom_app.model.Category;

@Service
public class CategoryService implements CategoryServiceInterface {
    private List<Category> categories = new ArrayList<>();

    public List<Category> getAllCategories() {
        return categories;
    }

    public List<Category> addCategory(Category category) {
        int id = categories.size();
        category.setCategoryId(id + 1L);
        categories.add(category);
        return categories;
    }

}
