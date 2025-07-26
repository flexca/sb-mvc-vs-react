package dev.compare.mvcvsreact.react.product.model;

import lombok.Data;

@Data
public class ProductSearchRequest {

    private String name;
    private String categoryId;
    private int limit = 20;
    private int skip = 0;
}
