package com.abnamro.recipes.service;

import com.abnamro.recipes.exception.ErrorType;
import com.abnamro.recipes.exception.RecordNotFoundException;
import com.abnamro.recipes.model.Recipe;
import com.abnamro.recipes.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Implementation class for service layer of recipe operations.
 */
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getRecipeByCriteria(String instructions, Integer numberOfServing, String ingredientIncluded, String ingredientExcluded, Boolean isVegetarian, Integer pageNumber, Integer pageSize) {
        return recipeRepository.findByMultipleParameters(instructions, numberOfServing, ingredientIncluded, ingredientExcluded, isVegetarian, pageNumber, pageSize);
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(String id, Recipe recipeDetails) {
        Recipe recipeFromDb = recipeRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + id));

        recipeFromDb.setInstructions(recipeDetails.getInstructions());
        recipeFromDb.setNumberOfServing(recipeDetails.getNumberOfServing());
        recipeFromDb.setIngredients(recipeDetails.getIngredients());

        return recipeRepository.save(recipeFromDb);
    }

    @Override
    public void deleteRecipeById(String id) {
        recipeRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + id));
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + id));
    }


}
