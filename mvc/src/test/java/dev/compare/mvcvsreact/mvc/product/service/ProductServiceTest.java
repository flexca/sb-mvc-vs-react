package dev.compare.mvcvsreact.mvc.product.service;

import dev.compare.mvcvsreact.mvc.category.mapper.CategoryMapper;
import dev.compare.mvcvsreact.mvc.category.mapper.CategoryMapperImpl;
import dev.compare.mvcvsreact.mvc.category.model.CategoryEntity;
import dev.compare.mvcvsreact.mvc.category.repository.CategoryRepository;
import dev.compare.mvcvsreact.mvc.category.service.CategoryService;
import dev.compare.mvcvsreact.mvc.common.datetime.DateProvider;
import dev.compare.mvcvsreact.mvc.common.exception.InvalidInputException;
import dev.compare.mvcvsreact.mvc.product.mapper.ProductMapper;
import dev.compare.mvcvsreact.mvc.product.mapper.ProductMapperImpl;
import dev.compare.mvcvsreact.mvc.product.model.ProductDto;
import dev.compare.mvcvsreact.mvc.product.model.ProductEntity;
import dev.compare.mvcvsreact.mvc.product.model.ProductRequest;
import dev.compare.mvcvsreact.mvc.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.offset;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static final String CATEGORY_ID = "category_id";
    private static final String CATEGORY_NAME = "Category";

    private static final String PRODUCT_ID = "product_id";
    private static final String OTHER_PRODUCT_ID = "other_id";
    private static final String PRODUCT_NAME = "Product";
    private static final String PRODUCT_DESCRIPTION = "Description";

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;

    private ProductService productService;

    @BeforeEach
    void init() {
        ProductMapper productMapper = new ProductMapperImpl();
        ProductValidator productValidator = new ProductValidator(productRepository, categoryRepository);
        CategoryMapper categoryMapper = new CategoryMapperImpl();
        DateProvider dateProvider = new DateProvider();
        productService = new ProductService(productRepository, productMapper, productValidator,
                categoryRepository, categoryMapper, dateProvider);
    }

    @Test
    void testUpdateSuccessfully() {

        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(createCategory()));
        when(productRepository.getById(any())).thenReturn(Optional.of(createProduct(PRODUCT_ID)));
        when(productRepository.findByNameAndCategory(any(), any())).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(createProduct(PRODUCT_ID));

        ProductRequest request = createProductRequest();

        ProductDto actual = productService.update(PRODUCT_ID, request);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(actual.getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
        assertThat(actual.getCategory()).isNotNull();
        assertThat(actual.getId()).isNotBlank();
        assertThat(actual.getCreatedAt()).isNotNull();
    }

    @Test
    void testUpdateFailSameNameExists() {

        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(createCategory()));
        when(productRepository.getById(any())).thenReturn(Optional.of(createProduct(PRODUCT_ID)));
        when(productRepository.findByNameAndCategory(any(), any())).thenReturn(Optional.of(createProduct(OTHER_PRODUCT_ID)));

        ProductRequest request = createProductRequest();

        Assertions.assertThrows(InvalidInputException.class, () ->
                productService.update(PRODUCT_ID, request));
    }

    private ProductRequest createProductRequest() {
        ProductRequest request = new ProductRequest();
        request.setName(PRODUCT_NAME);
        request.setCategoryId(CATEGORY_ID);
        request.setDescription(PRODUCT_DESCRIPTION);
        return request;
    }

    private CategoryEntity createCategory() {
        CategoryEntity category = new CategoryEntity();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);
        category.setCreatedAt(Date.from(ZonedDateTime.now().toInstant()));
        return category;
    }

    private ProductEntity createProduct(String id) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setName(PRODUCT_NAME);
        productEntity.setCategoryId(CATEGORY_ID);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        productEntity.setCreatedAt(Date.from(ZonedDateTime.now().toInstant()));
        return productEntity;
    }
}

