package com.example.ecom_app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecom_app.config.AppConstants;
import com.example.ecom_app.model.Category;
import com.example.ecom_app.payload.CategoryRequestDTO;
import com.example.ecom_app.payload.CategoryResponse;
import com.example.ecom_app.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("public/categories")
    public CategoryResponse getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortOrder) {
        return categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
    }

    @PostMapping("public/categories")
    public ResponseEntity<CategoryRequestDTO> addCategory(@Valid @RequestBody CategoryRequestDTO category) {
        CategoryRequestDTO categoryRequestDTO = categoryService.addCategory(category);
        return new ResponseEntity<>(categoryRequestDTO, HttpStatus.CREATED);
    }

    @PutMapping("public/categories")
    public ResponseEntity<CategoryRequestDTO> updateCategory(@Valid @RequestBody CategoryRequestDTO category) {
        CategoryRequestDTO updatedCat = categoryService.updateCategory(category);
        return new ResponseEntity<>(updatedCat, HttpStatus.OK);
        // try {
        // return categoryService.updateCategory(category);
        // } catch (ResponseStatusException e) {
        // return new ResponseEntity<String>(e.getReason(), e.getStatusCode());
        // }
    }

    @DeleteMapping("admin/categories/{category}")
    public ResponseEntity<CategoryRequestDTO> deleteCategory(@PathVariable long category) {
        CategoryRequestDTO deletedCat = categoryService.deleteCategory(category);
        return new ResponseEntity<>(deletedCat, HttpStatus.OK);
        // try {
        // return categoryService.deleteCategory(category);
        // } catch (ResponseStatusException e) {
        // return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        // }
    }
}
