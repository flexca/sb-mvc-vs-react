package dev.compare.mvcvsreact.mvc.category.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = CategoryEntity.COLLECTION_NAME)
public class CategoryEntity {

    public static final String COLLECTION_NAME = "category";

    @Id
    private String id;
    private String name;
    private Date createdAt;
}
