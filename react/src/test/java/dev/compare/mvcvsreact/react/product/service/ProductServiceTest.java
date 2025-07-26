package dev.compare.mvcvsreact.react.product.service;

import dev.compare.mvcvsreact.react.category.model.CategoryDto;
import dev.compare.mvcvsreact.react.category.service.CategoryService;
import dev.compare.mvcvsreact.react.common.datetime.DateProvider;
import dev.compare.mvcvsreact.react.common.exception.InvalidInputException;
import dev.compare.mvcvsreact.react.product.mapper.ProductMapper;
import dev.compare.mvcvsreact.react.product.mapper.ProductMapperImpl;
import dev.compare.mvcvsreact.react.product.model.ProductEntity;
import dev.compare.mvcvsreact.react.product.model.ProductRequest;
import dev.compare.mvcvsreact.react.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.ZonedDateTime;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static final String CATEGORY_ID = "category_id";
    private static final String CATEGORY_NAME = "Category";

    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "Product";
    private static final String PRODUCT_DESCRIPTION = "Description";

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryService categoryService;

    private ProductService productService;

    @BeforeEach
    void init() {
        ProductMapper productMapper = new ProductMapperImpl();
        ProductValidator productValidator = new ProductValidator();
        DateProvider dateProvider = new DateProvider();
        productService = new ProductService(productRepository, productMapper, productValidator,
                categoryService, dateProvider);
    }

    @Test
    void testUpdateSuccessfully() {

        when(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(Mono.just(createCategory()));
        when(productRepository.getById(any())).thenReturn(Mono.just(createProduct()));
        when(productRepository.sameNameAndCategoryExists(any(), any(), any())).thenReturn(Mono.just(Boolean.FALSE));
        when(productRepository.save(any())).thenReturn(Mono.just(createProduct()));

        ProductRequest request = createProductRequest();

        StepVerifier.create(productService.updateProduct(PRODUCT_ID, Mono.just(request)))
                .assertNext(actual-> {
                    assertThat(actual).isNotNull();
                    assertThat(actual.getName()).isEqualTo(PRODUCT_NAME);
                    assertThat(actual.getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
                    assertThat(actual.getCategory()).isNotNull();
                    assertThat(actual.getId()).isNotBlank();
                    assertThat(actual.getCreatedAt()).isNotNull();
                }).verifyComplete();
    }

    @Test
    void testUpdateFailSameNameExists() {

        when(categoryService.getCategoryById(CATEGORY_ID)).thenReturn(Mono.just(createCategory()));
        when(productRepository.getById(any())).thenReturn(Mono.just(createProduct()));
        when(productRepository.sameNameAndCategoryExists(any(), any(), any())).thenReturn(Mono.just(Boolean.TRUE));

        ProductRequest request = createProductRequest();

        StepVerifier.create(productService.updateProduct(PRODUCT_ID, Mono.just(request)))
                .expectError(InvalidInputException.class)
                .verify();
    }

    private ProductRequest createProductRequest() {
        ProductRequest request = new ProductRequest();
        request.setName(PRODUCT_NAME);
        request.setCategoryId(CATEGORY_ID);
        request.setDescription(PRODUCT_DESCRIPTION);
        return request;
    }

    private CategoryDto createCategory() {
        CategoryDto category = new CategoryDto();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);
        category.setCreatedAt(ZonedDateTime.now());
        return category;
    }

    private ProductEntity createProduct() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(PRODUCT_ID);
        productEntity.setName(PRODUCT_NAME);
        productEntity.setCategoryId(CATEGORY_ID);
        productEntity.setDescription(PRODUCT_DESCRIPTION);
        productEntity.setCreatedAt(Date.from(ZonedDateTime.now().toInstant()));
        return productEntity;
    }
}
