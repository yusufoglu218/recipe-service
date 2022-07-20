package com.abnamro.recipes.repository;

import com.abnamro.recipes.model.Recipe;

import java.util.List;

/**
 * Custom repository to build search method by multiple criteria
 */
public interface CustomRecipeRepository {

    /**
     * Find the recipe/s by multiple criteria
     *
     * @param instructions
     * @param numberOfServing
     * @param ingredientIncluded
     * @param ingredientExcluded
     * @param isVegetarian
     * @return
     */
    List<Recipe> findByMultipleParameters(String instructions, Integer numberOfServing, String ingredientIncluded, String ingredientExcluded, Boolean isVegetarian, Integer pageNumber, Integer pageSize );
}
