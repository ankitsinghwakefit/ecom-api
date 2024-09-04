package com.example.ecom_app.payload;

import java.util.List;

import lombok.Data;

@Data
public class CategoryResponse {
    private List<CategoryRequestDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
