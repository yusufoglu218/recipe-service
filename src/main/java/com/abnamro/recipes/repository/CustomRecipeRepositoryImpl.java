package com.abnamro.recipes.repository;

import com.abnamro.recipes.model.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Custom repository implementation to build search method by multiple criteria
 */
@Repository
public class CustomRecipeRepositoryImpl implements CustomRecipeRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public CustomRecipeRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Recipe> findByMultipleParameters(String instructions, Integer numberOfServing, String ingredientIncluded, String ingredientExcluded, Boolean isVegetarian, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());

        Query query = new Query().with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize());

        if (StringUtils.isNotEmpty(instructions)) {
            query.addCriteria(Criteria.where("instructions").regex(instructions, "i"));
        }
        if (null != numberOfServing) {
            query.addCriteria(Criteria.where("numberOfServing").is(numberOfServing));
        }
        if (StringUtils.isNotEmpty(ingredientIncluded) && StringUtils.isNotEmpty(ingredientExcluded)) {
            query.addCriteria(new Criteria().andOperator(
                    Criteria.where("ingredients.name").is(ingredientIncluded),
                    Criteria.where("ingredients.name").ne(ingredientExcluded)
            ));
        } else {
            if (StringUtils.isNotEmpty(ingredientIncluded)) {
                query.addCriteria(Criteria.where("ingredients.name").is(ingredientIncluded));
            }
            if (StringUtils.isNotEmpty(ingredientExcluded)) {
                query.addCriteria(Criteria.where("ingredients.name").ne(ingredientExcluded));
            }
        }
        if (null != isVegetarian) {
            if (isVegetarian == true) {
                query.addCriteria(Criteria.where("ingredients.isVegetarian").ne(false));
            } else {
                query.addCriteria(Criteria.where("ingredients.isVegetarian").is(false));
            }
        }

        return mongoTemplate.find(query, Recipe.class);
    }


}
