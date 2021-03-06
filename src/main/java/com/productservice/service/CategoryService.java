package com.productservice.service;

import com.productservice.domain.Category;
import com.productservice.web.rest.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Category.
 */
public interface CategoryService {

    /**
     * Save a category.
     * 
     * @param categoryDTO the entity to save
     * @return the persisted entity
     */
    CategoryDTO save(CategoryDTO categoryDTO);

    /**
     *  Get all the categories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Category> findAll(Pageable pageable);

    /**
     *  Get the "id" category.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    CategoryDTO findOne(Long id);

    /**
     *  Delete the "id" category.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
