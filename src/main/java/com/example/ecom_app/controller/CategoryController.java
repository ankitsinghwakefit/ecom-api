package com.example.ecom_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom_app.model.Category;
import com.example.ecom_app.service.CategoryService;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("api/public/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("api/public/categories")
    public List<Category> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
}
