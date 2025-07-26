package dev.compare.mvcvsreact.react.product.model;

import dev.compare.mvcvsreact.react.category.model.CategoryDto;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ProductDto {

    private String id;
    private CategoryDto category;
    private String name;
    private String description;
    private ZonedDateTime createdAt;
}
