package com.example.ecom_app.service;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom_app.exceptions.APIException;
import com.example.ecom_app.exceptions.MyNotFoundException;
import com.example.ecom_app.model.Category;
import com.example.ecom_app.model.Product;
import com.example.ecom_app.payload.ProductRequestDTO;
import com.example.ecom_app.payload.ProductResponse;
import com.example.ecom_app.repo.CategoryRepository;
import com.example.ecom_app.repo.ProductRepository;

@Service
public class ProductService implements ProductServiceInterface {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductRequestDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new MyNotFoundException("Category", "id", categoryId));
        product.setCategory(category);
        product.setImage("default.jpeg");
        double specialPrice = product.getPrice() * ((100 - product.getDiscount()) / 100);
        product.setSpecialPrice(specialPrice);
        return modelMapper.map(productRepository.save(product), ProductRequestDTO.class);
    }

    public ProductResponse getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductRequestDTO> prodDto = products.stream().map(prod -> {
            return modelMapper.map(prod, ProductRequestDTO.class);
        }).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(prodDto);
        return response;
    }

    public ProductResponse getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new MyNotFoundException("Category", "id", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductRequestDTO> prodDto = products.stream().map(prod -> {
            return modelMapper.map(prod, ProductRequestDTO.class);
        }).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(prodDto);
        return response;
    }

    public ProductResponse getProductsByKeyword(String keyword) {
        // List<Product> products = productRepository.findAll().stream()
        // .filter(prod ->
        // prod.getProductName().toUpperCase().contains(keyword.toUpperCase())).toList();

        // Like is used for pattern matching
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductRequestDTO> prodDto = products.stream().map(prod -> {
            return modelMapper.map(prod, ProductRequestDTO.class);
        }).toList();
        ProductResponse response = new ProductResponse();
        response.setContent(prodDto);
        return response;
    }
}
