package dev.compare.mvcvsreact.react.product.service;

import dev.compare.mvcvsreact.react.common.exception.InvalidInputException;
import dev.compare.mvcvsreact.react.product.model.ProductEntity;
import dev.compare.mvcvsreact.react.product.model.ProductRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    public void validateRequest(ProductRequest request, boolean update) {

        if(!update) {
            String categoryId = request.getCategoryId();
            if (StringUtils.isBlank(categoryId)) {
                throw new InvalidInputException("Provide category for product");
            }
        }

        String name = request.getName();
        if (StringUtils.isBlank(name)) {
            throw new InvalidInputException("Provide name of product");
        }
        if (name.length() > 64) {
            throw new InvalidInputException("Name of product should not exceed 64 chars");
        }

        String description = request.getDescription();
        if (StringUtils.isNotBlank(description) && description.length() > 255) {
            throw new InvalidInputException("Description of product should not exceed 64 chars");
        }
    }

    public ProductEntity validateNameUnique(ProductEntity toValidate, Boolean sameNameExists) {
        if(sameNameExists) {
            throw new InvalidInputException("Provide unique name for product");
        }
        return toValidate;
    }
}
