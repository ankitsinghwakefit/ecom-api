package com.example.ecom_app.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom_app.model.Category;
import com.example.ecom_app.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
