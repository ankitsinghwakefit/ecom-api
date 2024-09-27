package com.example.ecom_app.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecom_app.config.AppConstants;
import com.example.ecom_app.model.Product;
import com.example.ecom_app.payload.ProductRequestDTO;
import com.example.ecom_app.payload.ProductResponse;
import com.example.ecom_app.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getProducts(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortOrder) {
        return new ResponseEntity<>(productService.getProducts(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @GetMapping("public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategoryId(@PathVariable(name = "categoryId") Long categoryId) {
        return new ResponseEntity<>(productService.getProductsByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable(name = "keyword") String keyword) {
        return new ResponseEntity<>(productService.getProductsByKeyword(keyword), HttpStatus.FOUND);
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductRequestDTO> addProduct(@Valid @RequestBody ProductRequestDTO productDto,
            @PathVariable("categoryId") Long categoryId) {
        return new ResponseEntity<>(productService.addProduct(categoryId, productDto), HttpStatus.CREATED);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductRequestDTO> updateProduct(@Valid @RequestBody Product product,
            @PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.updateProduct(productId, product), HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        return new ResponseEntity<String>(productService.deleteProduct(productId), HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductRequestDTO> updateProductImage(@PathVariable("productId") Long productId,
            @RequestParam("image") MultipartFile multipartFile) {
        return new ResponseEntity<>(productService.uploadImage(productId, multipartFile), HttpStatus.OK);
    }
}
