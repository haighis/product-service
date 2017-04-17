package com.productservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.productservice.domain.Product;
import com.productservice.service.ProductService;
import com.productservice.web.rest.util.HeaderUtil;
import com.productservice.web.rest.util.PaginationUtil;
import com.productservice.web.rest.dto.ProductDTO;
import com.productservice.web.rest.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Product.
 * Not used in Shop UI. Used for add/update/delete product. 
 * TODO refactor and move Add/Update/Delete Verbs to a Product Admin Resource/Service. 
 * In theory these operations would be password protected so they should be in a separate admin service.
 * Contains Verbs on Product Resource for:
 * - POST - create a product. Used to enter new products into the system.
 * - PUT - update a product. Used to update an existing product.
  * - GET with Id - gets a single product
 * - DELETE - delete a product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);
        
    @Inject
    private ProductService productService;
    
    @Inject
    private ProductMapper productMapper;
    
     /**
     * POST  /products : Create a new product.
     *
     * @param productDTO the productDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productDTO, or with status 400 (Bad Request) if the product has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/products",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("product", "idexists", "A new product cannot already have an ID")).body(null);
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("product", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /products : Updates an existing product.
     *
     * @param productDTO the productDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productDTO,
     * or with status 400 (Bad Request) if the productDTO is not valid,
     * or with status 500 (Internal Server Error) if the productDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/products",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            return createProduct(productDTO);
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("product", productDTO.getId().toString()))
            .body(result);
    }

     /**
      * GET  /products/:id : get the "id" product.
      *
      * @param id the id of the productDTO to retrieve
      * @return the ResponseEntity with status 200 (OK) and with body the productDTO, or with status 404 (Not Found)
      */
     @RequestMapping(value = "/products/{id}",
         method = RequestMethod.GET,
         produces = MediaType.APPLICATION_JSON_VALUE)
     @Timed
     public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
         log.debug("REST request to get Product : {}", id);
         ProductDTO productDTO = productService.findOne(id);
         return Optional.ofNullable(productDTO)
             .map(result -> new ResponseEntity<>(
                 result,
                 HttpStatus.OK))
             .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
     }

    /**
     * DELETE  /products/:id : delete the "id" product.
     *
     * @param id the id of the productDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/products/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("product", id.toString())).build();
    }
}
