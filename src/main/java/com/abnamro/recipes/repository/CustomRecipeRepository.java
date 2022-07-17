package com.abnamro.recipes.repository;

import com.abnamro.recipes.model.Recipe;

import java.util.List;

/**
 * Custom repository to build search method by multiple criteria
 */
public interface CustomRecipeRepository {
    List<Recipe> findByMultipleParameters(String instructions, Integer numberOfServing, String ingredientIncluded, String ingredientExcluded, Boolean isVegetarian);
}
