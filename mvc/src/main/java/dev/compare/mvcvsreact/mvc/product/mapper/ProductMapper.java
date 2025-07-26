package dev.compare.mvcvsreact.mvc.product.mapper;

import dev.compare.mvcvsreact.mvc.category.model.CategoryDto;
import dev.compare.mvcvsreact.mvc.common.datetime.DateProvider;
import dev.compare.mvcvsreact.mvc.product.model.ProductDto;
import dev.compare.mvcvsreact.mvc.product.model.ProductEntity;
import dev.compare.mvcvsreact.mvc.product.model.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", source = "dateProvider.currentDate")
    ProductEntity toEntity(ProductRequest request, DateProvider dateProvider);

    @Mapping(target = "id", source = "existent.id")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "categoryId", source = "request.categoryId")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "createdAt", source = "existent.createdAt")
    ProductEntity toEntity(ProductRequest request, ProductEntity existent);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "category", source = "category")
    ProductDto toDto(ProductEntity entity, CategoryDto category);
}
