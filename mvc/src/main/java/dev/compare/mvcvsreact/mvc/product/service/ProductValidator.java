package dev.compare.mvcvsreact.mvc.product.service;

import dev.compare.mvcvsreact.mvc.category.repository.CategoryRepository;
import dev.compare.mvcvsreact.mvc.common.exception.EntityNotFoundException;
import dev.compare.mvcvsreact.mvc.common.exception.InvalidInputException;
import dev.compare.mvcvsreact.mvc.product.model.ProductEntity;
import dev.compare.mvcvsreact.mvc.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductValidator {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public void validateProduct(ProductEntity toValidate, ProductEntity existent) {

        String categoryId = toValidate.getCategoryId();
        if (StringUtils.isBlank(categoryId)) {
            throw new InvalidInputException("Provide category for product");
        }

        categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        String name = toValidate.getName();
        if (StringUtils.isBlank(name)) {
            throw new InvalidInputException("Provide name of product");
        }
        if (name.length() > 64) {
            throw new InvalidInputException("Name of product should not exceed 64 chars");
        }
        Optional<ProductEntity> sameName = productRepository.findByNameAndCategory(name, categoryId);
        if(sameName.isPresent() && (existent == null || !sameName.get().getId().equals(toValidate.getId()))) {
            throw new InvalidInputException("Provide unique name of product");
        }

        String description = toValidate.getDescription();
        if (StringUtils.isNotBlank(description) && description.length() > 255) {
            throw new InvalidInputException("Description of product should not exceed 64 chars");
        }
    }
}
