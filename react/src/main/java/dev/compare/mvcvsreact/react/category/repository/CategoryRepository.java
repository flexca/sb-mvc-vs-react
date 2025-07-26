package dev.compare.mvcvsreact.react.category.repository;

import dev.compare.mvcvsreact.react.category.model.CategoryEntity;
import dev.compare.mvcvsreact.react.category.model.CategorySearchRequest;
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
public class CategoryRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    public Mono<CategoryEntity> save(CategoryEntity toSave) {
        return mongoTemplate.save(toSave);
    }

    public Mono<CategoryEntity> findById(String id) {
        return mongoTemplate.findById(id, CategoryEntity.class);
    }

    public Flux<CategoryEntity> searchCategories(CategorySearchRequest request) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getName())) {
            criteria.and("name").regex("^.*" + request.getName() + ".*$");
        }
        return mongoTemplate.find(query(criteria).limit(request.getLimit()).skip(request.getSkip()), CategoryEntity.class);
    }

    public Mono<Boolean> existsWithSameName(String name) {
        Query query = query(where("name").is(name));
        return mongoTemplate.exists(query, CategoryEntity.class);
    }

    public Mono<Boolean> existsWithSameNameAndDifferentId(String id, String name) {
        Query query = query(where("name").is(name).and("_id").ne(id));
        return mongoTemplate.exists(query, CategoryEntity.class);
    }

}
