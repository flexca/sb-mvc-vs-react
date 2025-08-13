package dev.compare.mvcvsreact.react.product.repository;

import dev.compare.mvcvsreact.react.product.model.ProductEntity;
import dev.compare.mvcvsreact.react.product.model.ProductSearchRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<ProductEntity> save(ProductEntity toSave) {
        return mongoTemplate.save(toSave);
    }

    public Mono<ProductEntity> getById(String id) {
        return mongoTemplate.findById(id, ProductEntity.class);
    }

    public Mono<ProductEntity> findByNameAndCategoryId(String name, String categoryId) {
        Query query = query(where("name").is(name).and("categoryId").is(categoryId));
        return mongoTemplate.findOne(query, ProductEntity.class);
    }

    public Mono<Boolean> sameNameAndCategoryExists(String name, String categoryId) {
        Query query = query(where("name").is(name).and("categoryId").is(categoryId));
        return mongoTemplate.exists(query, ProductEntity.class);
    }

    public Mono<Boolean> sameNameAndCategoryExists(String name, String categoryId, String existentId) {
        Query query = query(where("name").is(name).and("categoryId").is(categoryId).and("_id").ne(existentId));
        return mongoTemplate.exists(query, ProductEntity.class);
    }

    public Flux<ProductEntity> search(ProductSearchRequest request) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getName())) {
            criteria.and("name").regex("^.*" + request.getName() + ".*$");
        }
        if (StringUtils.isNotBlank(request.getCategoryId())) {
            criteria.and("categoryId").is(request.getCategoryId());
        }
        return mongoTemplate.find(query(criteria).limit(request.getLimit()).skip(request.getSkip()), ProductEntity.class);
    }

    public Mono<ProductEntity> delete(String id) {
        Query query = query(where("_id").is(id));
        return mongoTemplate.findAndRemove(query, ProductEntity.class);
    }
}
