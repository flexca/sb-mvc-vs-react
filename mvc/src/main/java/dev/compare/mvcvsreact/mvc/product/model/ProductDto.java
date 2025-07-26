package dev.compare.mvcvsreact.mvc.product.model;

import dev.compare.mvcvsreact.mvc.category.model.CategoryDto;
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
