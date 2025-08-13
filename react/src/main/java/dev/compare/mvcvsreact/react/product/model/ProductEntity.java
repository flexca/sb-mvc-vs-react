package dev.compare.mvcvsreact.react.product.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = ProductEntity.COLLECTION_NAME)
public class ProductEntity {

    public static final String COLLECTION_NAME = "product";

    private String id;
    private String categoryId;
    private String name;
    private String description;
    private Date createdAt;
}
