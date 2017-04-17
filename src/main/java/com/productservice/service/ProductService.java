package com.productservice.service;

import com.productservice.domain.Product;
import com.productservice.web.rest.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Product.
 */
public interface ProductService {

    /**
     * Save a product.
     * 
     * @param productDTO the entity to save
     * @return the persisted entity
     */
    ProductDTO save(ProductDTO productDTO);

    /**
     *  Get all the products.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    
    /**
     *  Get the "id" product.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    ProductDTO findOne(Long id);

    /**
     *  Delete the "id" product.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
