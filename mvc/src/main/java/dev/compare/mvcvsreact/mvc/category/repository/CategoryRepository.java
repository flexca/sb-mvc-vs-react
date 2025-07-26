package dev.compare.mvcvsreact.mvc.category.repository;

import dev.compare.mvcvsreact.mvc.category.model.CategoryEntity;
import dev.compare.mvcvsreact.mvc.category.model.CategorySearchRequest;
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
public class CategoryRepository {

    private final MongoTemplate mongoTemplate;

    public CategoryEntity save(CategoryEntity toSave) {
        return mongoTemplate.save(toSave);
    }

    public Optional<CategoryEntity> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, CategoryEntity.class));
    }

    public CategoryEntity findByName(String name) {
        Query query = query(where("name").is(name));
        return mongoTemplate.findOne(query, CategoryEntity.class);
    }

    public List<CategoryEntity> search(CategorySearchRequest request) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(request.getName())) {
            criteria.and("name").regex("^.*" + request.getName() + ".*$");
        }
        return mongoTemplate.find(query(criteria).limit(request.getLimit()).skip(request.getSkip()), CategoryEntity.class);
    }
}
