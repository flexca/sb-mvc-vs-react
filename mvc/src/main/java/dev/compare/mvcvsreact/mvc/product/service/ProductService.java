package dev.compare.mvcvsreact.mvc.product.service;

import dev.compare.mvcvsreact.mvc.category.mapper.CategoryMapper;
import dev.compare.mvcvsreact.mvc.category.model.CategoryEntity;
import dev.compare.mvcvsreact.mvc.category.repository.CategoryRepository;
import dev.compare.mvcvsreact.mvc.common.datetime.DateProvider;
import dev.compare.mvcvsreact.mvc.common.exception.EntityNotFoundException;
import dev.compare.mvcvsreact.mvc.product.mapper.ProductMapper;
import dev.compare.mvcvsreact.mvc.product.model.ProductDto;
import dev.compare.mvcvsreact.mvc.product.model.ProductEntity;
import dev.compare.mvcvsreact.mvc.product.model.ProductRequest;
import dev.compare.mvcvsreact.mvc.product.model.ProductSearchRequest;
import dev.compare.mvcvsreact.mvc.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final DateProvider dateProvider;

    public ProductDto getById(String id) {
        ProductEntity entity = productRepository.getById(id).orElseThrow(() ->
                new EntityNotFoundException("Product not found"));
        return toDto(entity);
    }

    public List<ProductDto> search(ProductSearchRequest request) {
        List<ProductEntity> entities = productRepository.search(request);
        return entities.stream().map(this::toDto).toList();
    }

    public ProductDto create(ProductRequest request) {
        ProductEntity toSave = productMapper.toEntity(request, dateProvider);
        productValidator.validateProduct(toSave, null);
        ProductEntity saved = productRepository.save(toSave);
        return toDto(saved);
    }

    public ProductDto update(String id, ProductRequest request) {
        ProductEntity existing = productRepository.getById(id).orElseThrow(() ->
                new EntityNotFoundException("Product not found"));
        ProductEntity toUpdate = productMapper.toEntity(request, existing);
        productValidator.validateProduct(toUpdate, existing);
        ProductEntity updated = productRepository.save(toUpdate);
        return toDto(updated);
    }

    public ProductDto delete(String id) {
        ProductEntity toDelete = productRepository.getById(id).orElseThrow(() ->
                new EntityNotFoundException("Product not found"));
        productRepository.delete(id);
        return toDto(toDelete);
    }

    private ProductDto toDto(ProductEntity entity) {
        CategoryEntity categoryEntity = categoryRepository.findById(entity.getCategoryId()).orElseThrow(() ->
                new EntityNotFoundException("Category not found"));
        return productMapper.toDto(entity, categoryMapper.toDto(categoryEntity));
    }
}
