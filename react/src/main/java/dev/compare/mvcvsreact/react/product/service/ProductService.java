package dev.compare.mvcvsreact.react.product.service;

import dev.compare.mvcvsreact.react.category.model.CategoryDto;
import dev.compare.mvcvsreact.react.category.service.CategoryService;
import dev.compare.mvcvsreact.react.common.datetime.DateProvider;
import dev.compare.mvcvsreact.react.common.exception.EntityNotFoundException;
import dev.compare.mvcvsreact.react.product.mapper.ProductMapper;
import dev.compare.mvcvsreact.react.product.model.ProductEntity;
import dev.compare.mvcvsreact.react.product.model.ProductSearchRequest;
import dev.compare.mvcvsreact.react.product.repository.ProductRepository;
import dev.compare.mvcvsreact.react.product.model.ProductRequest;
import dev.compare.mvcvsreact.react.product.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;
    private final CategoryService categoryService;
    private final DateProvider dateProvider;

    public Mono<ProductDto> getProductById(String id) {
        Mono<ProductEntity> productEntity = productRepository.getById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found")));;
        Mono<CategoryDto> categoryDto =  categoryService.getCategoryById(productEntity.map(ProductEntity::getCategoryId));
        return Mono.zip(productEntity, categoryDto, productMapper::toDto);
    }

    public Flux<ProductDto> searchProducts(ProductSearchRequest searchRequest) {
        Flux<ProductEntity> entities = productRepository.search(searchRequest);
        return entities.flatMap(entity -> {
            Mono<CategoryDto> category = categoryService.getCategoryById(entity.getCategoryId());
            return Mono.just(entity).zipWith(category, productMapper::toDto);
        });
    }

    public Mono<ProductDto> createProduct(Mono<ProductRequest> createProductRequest) {
        return createProductRequest.flatMap(request -> {
            productValidator.validateRequest(request, false);
            Mono<CategoryDto> category = categoryService.getCategoryById(request.getCategoryId());
            Mono<ProductEntity> toValidate = Mono.just(productMapper.toEntity(request, dateProvider));
            Mono<Boolean> sameNameExists = productRepository.sameNameAndCategoryExists(request.getName(), request.getCategoryId());
            Mono<ProductEntity> toSave = Mono.zip(toValidate, sameNameExists, productValidator::validateNameUnique);
            Mono<ProductEntity> saved = toSave.flatMap(productRepository::save);
            return Mono.zip(saved, category, productMapper::toDto);
        });
    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductRequest> updateProductRequest) {

        return updateProductRequest.flatMap(request -> {
            productValidator.validateRequest(request, true);
            Mono<CategoryDto> category = categoryService.getCategoryById(request.getCategoryId());
            Mono<ProductEntity> existent = productRepository.getById(id)
                    .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found")));
            Mono<ProductEntity> toValidate = existent.map(entity -> productMapper.toEntity(request, entity));
            Mono<Boolean> sameNameExists = productRepository.sameNameAndCategoryExists(request.getName(), request.getCategoryId(), id);
            Mono<ProductEntity> toSave = Mono.zip(toValidate, sameNameExists, productValidator::validateNameUnique);
            Mono<ProductEntity> saved = toSave.flatMap(productRepository::save);
            return Mono.zip(saved, category, productMapper::toDto);
        });
    }

    public Mono<ProductDto> deleteProduct(String id) {
        Mono<ProductEntity> existent = productRepository.getById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found")));
        return existent.flatMap(entity -> {
            Mono<ProductEntity> deleted = productRepository.delete(id);
            Mono<CategoryDto> category = categoryService.getCategoryById(entity.getCategoryId());
            return Mono.zip(deleted, category, productMapper::toDto);
        });
    }
}
