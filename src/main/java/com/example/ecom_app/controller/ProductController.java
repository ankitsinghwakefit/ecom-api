package com.example.ecom_app.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom_app.model.Product;
import com.example.ecom_app.payload.ProductRequestDTO;
import com.example.ecom_app.payload.ProductResponse;
import com.example.ecom_app.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
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
    public ResponseEntity<ProductRequestDTO> addProduct(@PathVariable("categoryId") Long categoryId,
            @RequestBody Product product) {
        return new ResponseEntity<>(productService.addProduct(categoryId, product), HttpStatus.CREATED);
    }
}
