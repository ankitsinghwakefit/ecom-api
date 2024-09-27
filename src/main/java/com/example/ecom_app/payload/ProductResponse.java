package com.example.ecom_app.payload;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private List<ProductRequestDTO> content;
    private Integer pageNumber;
    private Integer pageSize;
    private long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
