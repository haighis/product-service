package com.productservice.web.rest;

import com.productservice.ProductserviceApp;
import com.productservice.domain.Category;
import com.productservice.domain.Product;
import com.productservice.repository.ProductRepository;
import com.productservice.service.CategoryService;
import com.productservice.service.ProductService;
import com.productservice.web.rest.dto.CategoryDTO;
import com.productservice.web.rest.dto.ProductDTO;
import com.productservice.web.rest.mapper.CategoryMapper;
import com.productservice.web.rest.mapper.ProductMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ProductserviceApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProductResourceIntTest {

    private static final String DEFAULT_SKU = "NEXUS5B";
    private static final String UPDATED_SKU = "NEXUS6";
    private static final String DEFAULT_TITLE = "Google Nexus 5";
    private static final String UPDATED_TITLE = "Google Nexus 6";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final String DEFAULT_BRAND = "Google";
    private static final String UPDATED_BRAND = "LG";
    private static final String DEFAULT_DESCRIPTION = "Enjoy this wonderful mobile.";
    private static final String UPDATED_DESCRIPTION = "Fanastic battery life";
    private static Category DEFAULT_CATEGORY; 

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductMapper productMapper;

    @Inject
    private ProductService productService;
    
    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductMockMvc;

    private Product product;
    
    private void CreateDefaultCategory() {
    	Category category = new Category();
    	category.setId(1000l);
    	category.setTitle("Mobiles");
    	DEFAULT_CATEGORY =  category;
    }
    
    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductAdminResource productResource = new ProductAdminResource();
        
        ReflectionTestUtils.setField(productResource, "productService", productService);
        ReflectionTestUtils.setField(productResource, "productMapper", productMapper);
        
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
   }

    @Before
    public void initTest() {
    	CreateDefaultCategory();
    	product = new Product();
        product.setSku(DEFAULT_SKU);
        product.setTitle(DEFAULT_TITLE);
        product.setPrice(DEFAULT_PRICE);
        product.setBrand(DEFAULT_BRAND);
        product.setDescription(DEFAULT_DESCRIPTION);
        product.setCategory(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
    	// Arrange
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        // Act
        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isCreated());

        // Assert
        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testProduct.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void checkSkuIsRequired() throws Exception {
        // Arrange
    	int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setSku(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        // Act
        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());
        // Assert
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        // Arrange
    	int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setTitle(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);
        
        // Act & Assert
        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        // Arrange
    	int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setPrice(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);
        
        // Act & Assert
        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        // Arrange 
    	int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setBrand(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);
        
        // Act & Assert
        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        // Arrange
    	int databaseSizeBeforeTest = productRepository.findAll().size();
        // set the field null
        product.setDescription(null);

        // Create the Product, which fails.
        ProductDTO productDTO = productMapper.productToProductDTO(product);
        
        // Act & Assert
        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isBadRequest());

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeTest);
    }
}
