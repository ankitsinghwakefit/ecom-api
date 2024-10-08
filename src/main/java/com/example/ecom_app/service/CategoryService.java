package com.example.ecom_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.ecom_app.exceptions.APIException;
import com.example.ecom_app.exceptions.MyNotFoundException;
import com.example.ecom_app.model.Category;
import com.example.ecom_app.payload.CategoryRequestDTO;
import com.example.ecom_app.payload.CategoryResponse;
import com.example.ecom_app.repo.CategoryRepository;

@Service
public class CategoryService implements CategoryServiceInterface {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    // private List<Category> categories = new ArrayList<>();

    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        // ModelMapper modelMapper = new ModelMapper();
        // TypeMap<Category, CategoryDTO> typeMap =
        // modelMapper.createTypeMap(Category.class, CategoryDTO.class);
        // categoryRepository.findAll().stream().map(cat -> {
        // typeMap.addMappings(mapper -> {
        // mapper.map(src -> src.get, CategoryDTO::categories);
        // });
        // });
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> result = categoryPage.getContent();
        // List<Category> result = categoryRepository.findAll();
        if (result.isEmpty()) {
            throw new APIException("There are no categories.");
        }
        List<CategoryRequestDTO> categoryRequestDTOs = result.stream()
                .map(eachCategory -> modelMapper.map(eachCategory, CategoryRequestDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryRequestDTOs);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
        // return ResponseEntity.ok("content");
        // return ResponseEntity.ok(categories);
    }

    public CategoryRequestDTO addCategory(CategoryRequestDTO category) {
        Category presenCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (presenCategory != null) {
            throw new APIException("category with name " + category.getCategoryName() + " already exists");
        }
        Category newCategory = modelMapper.map(category, Category.class);
        Category saved = categoryRepository.save(newCategory);
        // long id = System.currentTimeMillis();
        // category.setCategoryId(id);
        // categories.add(category);
        // return new ResponseEntity<>("category added successfully",
        // HttpStatus.CREATED);
        return modelMapper.map(saved, CategoryRequestDTO.class);
        // return ResponseEntity.ok("category added successfully");
    }

    public CategoryRequestDTO updateCategory(CategoryRequestDTO category) {

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
            Category updateCategory = modelMapper.map(category, Category.class);
            Category savedCat = categoryRepository.save(updateCategory);
            return modelMapper.map(savedCat, CategoryRequestDTO.class);
        } else {
            throw new MyNotFoundException("category", "ID", categoryId);
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

    public CategoryRequestDTO deleteCategory(long category) {
        Category tarCategory = categoryRepository.findById(category)
                .orElseThrow(() -> new MyNotFoundException("category", "ID", category));
        categoryRepository.delete(tarCategory);
        return modelMapper.map(tarCategory, CategoryRequestDTO.class);
        // if (targeCategory.isPresent()) {
        // categoryRepository.deleteById(category);
        // return modelMapper.map(targeCategory, CategoryRequestDTO.class);
        // // return new ResponseEntity<>("category " + category + " deleted
        // successfully",
        // // HttpStatus.OK);
        // }
        // else {
        // throw new MyNotFoundException("category", "ID", category);
        // }

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
