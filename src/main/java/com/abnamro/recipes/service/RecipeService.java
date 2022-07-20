package com.abnamro.recipes.service;


import com.abnamro.recipes.model.Recipe;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Interface for recipe services.
 */
public interface RecipeService {

    /**
     * Get recipe/s by given parameters
     * @param instructions
     * @param numberOfServing
     * @param ingredientIncluded
     * @param ingredientExcluded
     * @param isVegetarian
     * @return
     */
    List<Recipe> getRecipeByCriteria(String instructions, Integer numberOfServing, String ingredientIncluded, String ingredientExcluded, Boolean isVegetarian, Integer pageNumber, Integer pageSize);

    /**
     * Get recipe by id
     * @param id
     * @return
     */
    Recipe getRecipeById(String id);

    /**
     * Save recipe by the object parameter
     * @param recipe
     * @return
     */
    Recipe saveRecipe(Recipe recipe);

    /**
     * Update recipe by id and recipe object
     * @param id
     * @param recipe
     * @return
     */
    Recipe updateRecipe(String id,Recipe recipe);

    /**
     * Delete recipe by id
     * @param id
     */
    void deleteRecipeById(String id);

}
