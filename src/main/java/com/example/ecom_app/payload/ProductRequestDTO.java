package com.example.ecom_app.payload;

import com.example.ecom_app.model.Category;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private long productId;
    private String productName;
    private String description;
    private String image;
    private Integer quantity;
    private double price;
    private double specialPrice;
    private Category category;
    private double discount;
}
