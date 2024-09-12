package com.example.ecom_app.service;

import java.util.*;
import java.util.UUID;
import java.io.File;
import java.nio.file.Files;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

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

    public ProductRequestDTO addProduct(Long categoryId, ProductRequestDTO product) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new MyNotFoundException("Category", "id", categoryId));
        product.setCategory(category);
        product.setImage("default.jpeg");
        double specialPrice = product.getPrice() * ((100 - product.getDiscount()) / 100);
        product.setSpecialPrice(specialPrice);
        Product newProduct = modelMapper.map(product, Product.class);
        return modelMapper.map(productRepository.save(newProduct), ProductRequestDTO.class);
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
        String path = "images/";

        String fileName = uploadImage(path, imageFile);

        // updating the image file name in product
        existingProduct.setImage(fileName);
        Product savedProduct = productRepository.save(existingProduct);

        return modelMapper.map(savedProduct, ProductRequestDTO.class);
    }

    private String uploadImage(String path, MultipartFile imageFile) {
        String originalImageName = imageFile.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String newImageName = randomId.concat(originalImageName.substring(originalImageName.lastIndexOf('.')));
        String filePath = path + File.separator + newImageName;

        // check if path already exists if not create
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }

        // save the file to given path
        Files.copy(imageFile.getInputStream(), null)
    }
}
