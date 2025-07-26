package dev.compare.mvcvsreact.mvc.category.service;

import dev.compare.mvcvsreact.mvc.category.mapper.CategoryMapper;
import dev.compare.mvcvsreact.mvc.category.model.CategoryDto;
import dev.compare.mvcvsreact.mvc.category.model.CategoryEntity;
import dev.compare.mvcvsreact.mvc.category.model.CategoryRequest;
import dev.compare.mvcvsreact.mvc.category.model.CategorySearchRequest;
import dev.compare.mvcvsreact.mvc.category.repository.CategoryRepository;
import dev.compare.mvcvsreact.mvc.common.datetime.DateProvider;
import dev.compare.mvcvsreact.mvc.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;
    private final CategoryMapper categoryMapper;
    private final DateProvider dateProvider;

    public CategoryDto getById(String id) {

        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found"));
        return categoryMapper.toDto(categoryEntity);
    }

    public List<CategoryDto> search(CategorySearchRequest request) {
        List<CategoryEntity> entities = categoryRepository.search(request);
        return entities.stream().map(categoryMapper::toDto).toList();
    }

    public CategoryDto create(CategoryRequest request) {

        CategoryEntity toSave = categoryMapper.toEntity(request, dateProvider);
        categoryValidator.validateCategory(toSave, null);
        CategoryEntity saved = categoryRepository.save(toSave);
        return categoryMapper.toDto(saved);
    }

    public CategoryDto update(String id, CategoryRequest request) {

        CategoryEntity existent = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found"));
        CategoryEntity toSave = categoryMapper.toEntity(request, existent);
        categoryValidator.validateCategory(toSave, existent);
        CategoryEntity saved = categoryRepository.save(toSave);
        return categoryMapper.toDto(saved);
    }


}
