package com.example.ecom_app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecom_app.model.Product;
import com.example.ecom_app.payload.ProductRequestDTO;
import com.example.ecom_app.payload.ProductResponse;
import com.example.ecom_app.repo.ProductRepository;

@Service
public class ProductService implements ProductServiceInterface {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductResponse addProduct(Long categoryId, Product productRequestDTO){
        return productRepository.save(null)
    }
}
