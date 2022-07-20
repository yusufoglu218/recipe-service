package com.abnamro.recipes.repository;

import com.abnamro.recipes.model.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository class for crud operations
 */
public interface RecipeRepository extends CrudRepository<Recipe, String>, CustomRecipeRepository{
}
