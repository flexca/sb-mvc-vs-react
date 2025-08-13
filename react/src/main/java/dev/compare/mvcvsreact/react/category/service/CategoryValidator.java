package dev.compare.mvcvsreact.react.category.service;

import dev.compare.mvcvsreact.react.category.model.CategoryEntity;
import dev.compare.mvcvsreact.react.common.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    public CategoryEntity validateCategory(CategoryEntity toValidate, Boolean sameNameExists) {

        if(StringUtils.isBlank(toValidate.getName())) {
            throw new InvalidInputException("Provide category name");
        }

        if(toValidate.getName().length() > 64) {
            throw new InvalidInputException("Category name should not exceed 64 chars");
        }

        if(Boolean.TRUE.equals(sameNameExists)) {
            throw new InvalidInputException("Provide unique category name");
        }

        return toValidate;
    }

}
