package com.example.ecom_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "categories") // name our table name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;

    @NotBlank(message = "please provide a category name")
    @Size(min = 2, message = "category name must be at least 2 characters long")
    private String categoryName;

    // public Long getCategoryId() {
    // return categoryId;
    // }

    // public void setCategoryId(Long categoryId) {
    // this.categoryId = categoryId;
    // }

    // public String getCategoryName() {
    // return categoryName;
    // }

    // public void setCategoryName(String categoryName) {
    // this.categoryName = categoryName;
    // }

    // public Category(Long categoryId, String categoryName) {
    // this.categoryId = categoryId;
    // this.categoryName = categoryName;
    // }

    // public Category() {
    // }

}
