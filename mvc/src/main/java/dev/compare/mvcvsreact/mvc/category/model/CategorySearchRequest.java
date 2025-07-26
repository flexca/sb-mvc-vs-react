package dev.compare.mvcvsreact.mvc.category.model;

import lombok.Data;

@Data
public class CategorySearchRequest {

    private String name;
    private int limit = 10;
    private int skip = 0;
}
