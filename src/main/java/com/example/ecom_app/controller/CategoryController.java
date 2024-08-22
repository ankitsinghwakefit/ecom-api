package com.example.ecom_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecom_app.model.Category;
import com.example.ecom_app.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("public/categories")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PutMapping("public/categories")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category) {
        return categoryService.updateCategory(category);
        // try {
        // return categoryService.updateCategory(category);
        // } catch (ResponseStatusException e) {
        // return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
        // }
    }

    @DeleteMapping("admin/categories/{category}")
    public ResponseEntity<String> deleteCategory(@PathVariable long category) {
        return categoryService.deleteCategory(category);
        // try {
        // return categoryService.deleteCategory(category);
        // } catch (ResponseStatusException e) {
        // return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        // }
    }
}
