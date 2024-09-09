package com.example.ecom_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // @PostMapping("/admin/categories/{categoryId}/product")
    // public ResponseEntity<ProductResponse> addProduct(@PathVariable("categoryId")
    // String categoryId,
    // @RequestBody ProductRequestDTO productRequestDTO) {
    // return new ResponseEntity<>(productService.addProduct(categoryId,
    // productRequestDTO), HttpStatus.CREATED);
    // }
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductResponse> addProduct(@PathVariable("categoryId") Long categoryId,
            @RequestBody Product productRequestDTO) {
        return new ResponseEntity<>(productService.addProduct(categoryId, productRequestDTO), HttpStatus.CREATED);
    }
}
