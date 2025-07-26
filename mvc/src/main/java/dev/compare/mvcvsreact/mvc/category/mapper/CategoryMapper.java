package dev.compare.mvcvsreact.mvc.category.mapper;

import dev.compare.mvcvsreact.mvc.category.model.CategoryDto;
import dev.compare.mvcvsreact.mvc.category.model.CategoryEntity;
import dev.compare.mvcvsreact.mvc.category.model.CategoryRequest;
import dev.compare.mvcvsreact.mvc.common.datetime.DateProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(CategoryEntity categoryEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", source = "dateProvider.currentDate")
    CategoryEntity toEntity(CategoryRequest request, DateProvider dateProvider);

    @Mapping(target = "name", source = "request.name")
    CategoryEntity toEntity(CategoryRequest request, CategoryEntity existent);

}
