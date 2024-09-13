package com.example.ecom_app.service;

import java.util.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.ecom_app.config.AppConstants;
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

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    public ProductRequestDTO addProduct(Long categoryId, ProductRequestDTO product) {
        // check if product already exists or not
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new MyNotFoundException("Category", "id", categoryId));

        List<Product> isExistingProduct = productRepository.findByProductName(product.getProductName());
        if (isExistingProduct.size() > 0) {
            throw new APIException("This product already exists");
        }
        product.setCategory(category);
        product.setImage("default.jpeg");
        double specialPrice = product.getPrice() * ((100 - product.getDiscount()) / 100);
        product.setSpecialPrice(specialPrice);
        Product newProduct = modelMapper.map(product, Product.class);
        return modelMapper.map(productRepository.save(newProduct), ProductRequestDTO.class);
    }

    public ProductResponse getProducts() {
        // check is product size is 0 or not
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new APIException("No products found");
        }
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

    public ProductRequestDTO updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new MyNotFoundException("Product", "id", productId));
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setPrice(product.getPrice());
        double specialPrice = product.getPrice() * ((100 - product.getDiscount()) / 100);
        existingProduct.setSpecialPrice(specialPrice);
        Product saveProduct = productRepository.save(existingProduct);
        return modelMapper.map(saveProduct, ProductRequestDTO.class);
    }

    public String deleteProduct(Long productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new MyNotFoundException("Product", "id", productId));

        productRepository.delete(existingProduct);
        return "Product with id " + productId + " deleted";
    }

    public ProductRequestDTO uploadImage(Long productId, MultipartFile imageFile) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new MyNotFoundException("Product", "id", productId));

        // Upload the image to server
        // get the file name of uploaded image
        // String path = AppConstants.IMAGE_PATH_STRING;

        String fileName = fileService.uploadImage(path, imageFile);

        // updating the image file name in product
        existingProduct.setImage(fileName);
        Product savedProduct = productRepository.save(existingProduct);

        return modelMapper.map(savedProduct, ProductRequestDTO.class);
    }
}
