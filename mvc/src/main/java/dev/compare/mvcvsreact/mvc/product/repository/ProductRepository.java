package dev.compare.mvcvsreact.mvc.product.repository;

import dev.compare.mvcvsreact.mvc.product.model.ProductEntity;
import dev.compare.mvcvsreact.mvc.product.model.ProductSearchRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final MongoTemplate mongoTemplate;

    public Optional<ProductEntity> getById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, ProductEntity.class));
    }

    public ProductEntity save(ProductEntity toSave) {
        return mongoTemplate.save(toSave);
    }

    public List<ProductEntity> search(ProductSearchRequest request) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getName())) {
            criteria.and("name").regex("^.*" + request.getName() + ".*$");
        }
        if (StringUtils.isNotBlank(request.getCategoryId())) {
            criteria.and("categoryId").is(request.getCategoryId());
        }
        return mongoTemplate.find(query(criteria).limit(request.getLimit()).skip(request.getSkip()), ProductEntity.class);
    }

    public Optional<ProductEntity> findByNameAndCategory(String name, String categoryId) {
        Query query = query(where("name").is(name).and("categoryId").is(categoryId));
        return Optional.ofNullable(mongoTemplate.findOne(query, ProductEntity.class));
    }

    public void delete(String id) {
        Query query = query(where("_id").is(id));
        mongoTemplate.remove(query, ProductEntity.class);
    }
}
