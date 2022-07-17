package com.abnamro.recipes.controller;


import com.abnamro.recipes.model.Recipe;
import com.abnamro.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for recipe rest api
 */
@Tag(name = "Recipe-Controller", description = "Recipe Rest API")
@Slf4j
@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<Recipe> getRecipes(@RequestParam(required = false) String instructionsLike,
                                   @RequestParam(required = false) Integer numberOfServing,
                                   @RequestParam(required = false) String ingredientIncluded,
                                   @RequestParam(required = false) String ingredientExcluded,
                                   @RequestParam(required = false) Boolean isVegetarian) {

        return recipeService.getRecipeByCriteria(instructionsLike, numberOfServing, ingredientIncluded, ingredientExcluded, isVegetarian);
    }

    @GetMapping("{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteRecipeById(@PathVariable String id) {
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Recipe> updateRecipeById(@PathVariable String id, @RequestBody Recipe recipeDetails) {
        Recipe updateRecipe = recipeService.updateRecipe(id, recipeDetails);
        return ResponseEntity.ok(updateRecipe);
    }


}
