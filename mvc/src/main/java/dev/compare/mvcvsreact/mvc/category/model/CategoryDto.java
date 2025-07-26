package dev.compare.mvcvsreact.mvc.category.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class CategoryDto {

    private String id;
    private String name;
    private ZonedDateTime createdAt;
}
