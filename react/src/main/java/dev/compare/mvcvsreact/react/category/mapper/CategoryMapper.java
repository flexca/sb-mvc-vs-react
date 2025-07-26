package dev.compare.mvcvsreact.react.category.mapper;

import dev.compare.mvcvsreact.react.category.model.CategoryDto;
import dev.compare.mvcvsreact.react.category.model.CategoryEntity;
import dev.compare.mvcvsreact.react.category.model.CategoryRequest;
import dev.compare.mvcvsreact.react.category.model.CategorySearchRequest;
import dev.compare.mvcvsreact.react.common.datetime.DateProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.reactive.function.server.ServerRequest;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(CategoryEntity categoryEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", source = "dateProvider.currentDate")
    CategoryEntity toEntity(CategoryRequest request, DateProvider dateProvider);

    @Mapping(target = "name", source = "request.name")
    CategoryEntity toEntity(CategoryRequest request, CategoryEntity existent);

    default CategorySearchRequest toSearchRequest(ServerRequest request) {

        CategorySearchRequest searchRequest = new CategorySearchRequest();
        request.queryParam("name").ifPresent(searchRequest::setName);
        request.queryParam("limit").ifPresent(limit -> {
            searchRequest.setLimit(Integer.parseInt(limit));
        });
        request.queryParam("skip").ifPresent(skip -> {
            searchRequest.setSkip(Integer.parseInt(skip));
        });
        return searchRequest;
    }
}
