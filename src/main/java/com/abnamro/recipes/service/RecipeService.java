package com.abnamro.recipes.service;


import com.abnamro.recipes.model.Recipe;

import java.util.List;

/**
 * Interface for recipe services.
 */
public interface RecipeService {

    List<Recipe> getRecipeByCriteria(String instructions, Integer numberOfServing, String ingredientIncluded, String ingredientExcluded, Boolean isVegetarian);

    Recipe getRecipeById(String id);

    Recipe saveRecipe(Recipe recipe);

    Recipe updateRecipe(String id,Recipe recipe);

    void deleteRecipeById(String id);

}
