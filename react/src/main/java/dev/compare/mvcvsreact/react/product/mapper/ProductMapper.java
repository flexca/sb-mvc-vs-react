package dev.compare.mvcvsreact.react.product.mapper;

import dev.compare.mvcvsreact.react.category.model.CategoryDto;
import dev.compare.mvcvsreact.react.common.datetime.DateProvider;
import dev.compare.mvcvsreact.react.product.model.ProductDto;
import dev.compare.mvcvsreact.react.product.model.ProductEntity;
import dev.compare.mvcvsreact.react.product.model.ProductRequest;
import dev.compare.mvcvsreact.react.product.model.ProductSearchRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.reactive.function.server.ServerRequest;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", source = "dateProvider.currentDate")
    ProductEntity toEntity(ProductRequest request, DateProvider dateProvider);

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "categoryId", source = "existent.categoryId")
    ProductEntity toEntity(ProductRequest request, ProductEntity existent);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    @Mapping(target = "category", source = "category")
    ProductDto toDto(ProductEntity entity, CategoryDto category);

    default ProductSearchRequest toSearchRequest(ServerRequest request) {

        ProductSearchRequest searchRequest = new ProductSearchRequest();
        request.queryParam("name").ifPresent(searchRequest::setName);
        request.queryParam("categoryId").ifPresent(searchRequest::setCategoryId);
        request.queryParam("limit").ifPresent(limit -> {
            searchRequest.setLimit(Integer.parseInt(limit));
        });
        request.queryParam("skip").ifPresent(skip -> {
            searchRequest.setSkip(Integer.parseInt(skip));
        });
        return searchRequest;
    }
}
