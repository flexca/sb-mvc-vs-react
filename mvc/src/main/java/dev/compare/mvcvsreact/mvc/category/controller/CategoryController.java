package dev.compare.mvcvsreact.mvc.category.controller;

import dev.compare.mvcvsreact.mvc.category.model.CategoryDto;
import dev.compare.mvcvsreact.mvc.category.model.CategoryRequest;
import dev.compare.mvcvsreact.mvc.category.model.CategorySearchRequest;
import dev.compare.mvcvsreact.mvc.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable("id") String id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public List<CategoryDto> search(CategorySearchRequest request) {
        return categoryService.search(request);
    }

    @PostMapping
    public CategoryDto create(CategoryRequest request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable("id") String id, CategoryRequest request) {
        return categoryService.update(id, request);
    }


}
