package dev.compare.mvcvsreact.react.category.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CategoryDto {

    private String id;
    private String name;
    private ZonedDateTime createdAt;
}
