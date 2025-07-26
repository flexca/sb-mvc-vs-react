package dev.compare.mvcvsreact.react.category.service;

import dev.compare.mvcvsreact.react.category.mapper.CategoryMapper;
import dev.compare.mvcvsreact.react.category.model.CategoryDto;
import dev.compare.mvcvsreact.react.category.model.CategoryEntity;
import dev.compare.mvcvsreact.react.category.model.CategoryRequest;
import dev.compare.mvcvsreact.react.category.model.CategorySearchRequest;
import dev.compare.mvcvsreact.react.category.repository.CategoryRepository;
import dev.compare.mvcvsreact.react.common.datetime.DateProvider;
import dev.compare.mvcvsreact.react.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryValidator categoryValidator;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final DateProvider dateProvider;

    public Mono<CategoryDto> getCategoryById(String id) {
        return categoryRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Category not found")))
                .map(categoryMapper::toDto);
    }

    public Mono<CategoryDto> getCategoryById(Mono<String> id) {
        return id.flatMap(categoryRepository::findById)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Category not found")))
                .map(categoryMapper::toDto);
    }

    public Flux<CategoryDto> searchCategories(CategorySearchRequest searchRequest) {
        return categoryRepository.searchCategories(searchRequest)
                .map(categoryMapper::toDto);
    }

    public Mono<CategoryDto> createCategory(Mono<CategoryRequest> createRequest) {

        return createRequest.flatMap(request -> {
            Mono<CategoryEntity> toCreate = Mono.just(categoryMapper.toEntity(request, dateProvider));
            Mono<Boolean> sameNameExists = categoryRepository.existsWithSameName(request.getName());
            return Mono.zip(toCreate, sameNameExists, categoryValidator::validateCategory)
                    .onErrorResume(Mono::error)
                    .flatMap(categoryRepository::save)
                    .map(categoryMapper::toDto);
        });
    }

    public Mono<CategoryDto> updateCategory(String id, Mono<CategoryRequest> updateRequest) {

        return updateRequest.flatMap(request -> {
            Mono<CategoryEntity> existing = categoryRepository.findById(id)
                    .switchIfEmpty(Mono.error(new EntityNotFoundException("Category not found")));
            Mono<CategoryEntity> toUpdate = existing.map(existingCategory -> categoryMapper.toEntity(request, existingCategory));
            Mono<Boolean> sameNameExists = categoryRepository.existsWithSameNameAndDifferentId(id, request.getName());
            return Mono.zip(toUpdate, sameNameExists, categoryValidator::validateCategory)
                    .onErrorResume(Mono::error)
                    .flatMap(categoryRepository::save)
                    .map(categoryMapper::toDto);
        });
    }
}
