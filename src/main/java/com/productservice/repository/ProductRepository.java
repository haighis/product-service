package com.productservice.repository;

import com.productservice.domain.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
	Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}
