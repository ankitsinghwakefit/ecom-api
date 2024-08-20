package com.example.ecom_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecom_app.model.Category;
import com.example.ecom_app.repo.CategoryRepository;

@Service
public class CategoryService implements CategoryServiceInterface {
    @Autowired
    private CategoryRepository categoryRepository;
    // private List<Category> categories = new ArrayList<>();

    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryRepository.findAll());
        // return ResponseEntity.ok(categories);
    }

    public ResponseEntity<String> addCategory(Category category) {
        categoryRepository.save(category);
        // long id = System.currentTimeMillis();
        // category.setCategoryId(id);
        // categories.add(category);
        return new ResponseEntity<>("category added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateCategory(Category category) {

        // int position = -1;
        // System.out.println("category " + category.getCategoryId());
        // for (int i = 0; i < categories.size(); i++) {
        // System.out.println("category index " + i);
        // if (categories.get(i).getCategoryId() == category.getCategoryId()) {
        // System.out.println("category loop " + position + " " + i);
        // position = i;
        // break;
        // }
        // }
        // System.out.println("category " + position);
        long categoryId = category.getCategoryId();
        Optional<Category> targeCategory = categoryRepository.findById(categoryId);
        if (targeCategory.isPresent()) {
            categoryRepository.save(category);
            return new ResponseEntity<>("category updated successfully", HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }
        // Optional<Category> targetCategory = categories.stream()
        // .filter(cate -> cate.getCategoryId() == categoryId).findFirst();
        // if (targetCategory.isPresent()) {
        // targetCategory.get().setCategoryName(category.getCategoryName());
        // return new ResponseEntity<>("category updated successfully", HttpStatus.OK);
        // } else {
        // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid
        // category");
        // }
    }

    public ResponseEntity<String> deleteCategory(long category) {
        Optional<Category> targeCategory = categoryRepository.findById(category);

        if (targeCategory.isPresent()) {
            categoryRepository.deleteById(category);
            return new ResponseEntity<>("category " + category + " deleted successfully", HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
        }

        // Category targeCategory = categories.stream()
        // .filter(cate -> cate.getCategoryId() == category)
        // .findFirst()
        // .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
        // "Category not found"));

        // categories.remove(targeCategory);
        // return new ResponseEntity<>("category " + category + " deleted successfully",
        // HttpStatus.OK);

        // Category targeCategory = categories.stream()
        // .filter(cate -> cate.getCategoryId() == category)
        // .findFirst()
        // .orElse(null);

        // if (targeCategory != null) {
        // categories.remove(targeCategory);
        // return new ResponseEntity<>("category " + category + " deleted successfully",
        // HttpStatus.OK);
        // } else {
        // return new ResponseEntity<>("category " + category + " Not found",
        // HttpStatus.BAD_REQUEST);
        // }

        // we can also use stream to filter by category and pass thet category object in
        // remove to delete category
        // int position = -1;
        // for (int i = 0; i < categories.size(); i++) {
        // if (categories.get(i).getCategoryId() == category) {
        // position = i;
        // break;
        // }
        // }
        // if (position >= 0) {
        // categories.remove(position);
        // }
        // return categories;
    }

}
