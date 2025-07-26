package dev.compare.mvcvsreact.react.category.routing;

import dev.compare.mvcvsreact.react.category.mapper.CategoryMapper;
import dev.compare.mvcvsreact.react.category.model.CategoryDto;
import dev.compare.mvcvsreact.react.category.service.CategoryService;
import dev.compare.mvcvsreact.react.category.model.CategoryRequest;
import dev.compare.mvcvsreact.react.common.exception.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CategoryHandler {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final ErrorMapper errorMapper;

    public Mono<ServerResponse> getCategoryHandler(ServerRequest request) {
        return categoryService.getCategoryById(request.pathVariable("id"))
                .flatMap(responseBody -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseBody))
                .onErrorResume(errorMapper::toResponse);
    }

    public Mono<ServerResponse> searchCategoryHandler(ServerRequest request) {
        Flux<CategoryDto> result = categoryService.searchCategories(categoryMapper.toSearchRequest(request));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(result, CategoryDto.class);
    }

    public Mono<ServerResponse> createCategoryHandler(ServerRequest request) {
        return categoryService.createCategory(request.bodyToMono(CategoryRequest.class))
                .flatMap(responseBody -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseBody))
                .onErrorResume(errorMapper::toResponse);
    }

    public Mono<ServerResponse> updateCategoryHandler(ServerRequest request) {
        return categoryService.updateCategory(request.pathVariable("id"), request.bodyToMono(CategoryRequest.class))
                .flatMap(responseBody -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(responseBody))
                .onErrorResume(errorMapper::toResponse);
    }
}
