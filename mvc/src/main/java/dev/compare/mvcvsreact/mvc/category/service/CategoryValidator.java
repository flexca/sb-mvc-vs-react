package dev.compare.mvcvsreact.mvc.category.service;

import dev.compare.mvcvsreact.mvc.category.model.CategoryEntity;
import dev.compare.mvcvsreact.mvc.category.repository.CategoryRepository;
import dev.compare.mvcvsreact.mvc.common.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public void validateCategory(CategoryEntity toValidate, CategoryEntity existent) {

        if (StringUtils.isBlank(toValidate.getName())) {
            throw new InvalidInputException("Provide category name");
        }

        if (toValidate.getName().length() > 64) {
            throw new InvalidInputException("Category name should not exceed 64 chars");
        }

        CategoryEntity sameName = categoryRepository.findByName(toValidate.getName());

        if(sameName != null && (existent == null || existent.getId().equals(toValidate.getId()))) {
            throw new InvalidInputException("Provide unique category name");
        }
    }
}
