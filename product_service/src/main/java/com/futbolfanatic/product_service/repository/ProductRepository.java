package com.futbolfanatic.product_service.repository;

import com.futbolfanatic.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
