package dev.compare.mvcvsreact.react.product.model;

import lombok.Data;

@Data
public class ProductRequest {
    private String categoryId;
    private String name;
    private String description;
}
