package com.abnamro.recipes.controller;


import com.abnamro.recipes.exception.RecordNotFoundException;
import com.abnamro.recipes.model.Recipe;
import com.abnamro.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get recipe/s by multiple criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @GetMapping
    public List<Recipe> getRecipes(@Parameter(description = "Keyword within instructions of the recipe") @RequestParam(required = false) String instructionsLike,
                                   @Parameter(description = "NumberOfServing value of the recipe") @RequestParam(required = false) Integer numberOfServing,
                                   @Parameter(description = "Ingredient value exists in the recipe") @RequestParam(required = false) String ingredientIncluded,
                                   @Parameter(description = "Ingredient value not exist in the recipe") @RequestParam(required = false) String ingredientExcluded,
                                   @Parameter(description = "IsVegetarian value of the recipe") @RequestParam(required = false) Boolean isVegetarian) {

        return recipeService.getRecipeByCriteria(instructionsLike, numberOfServing, ingredientIncluded, ingredientExcluded, isVegetarian);
    }

    @Operation(summary = "Get the recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @GetMapping("{id}")
    public ResponseEntity<Recipe> getRecipeById(@Parameter(description = "Id of the recipe") @PathVariable String id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @Operation(summary = "Get the recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @PostMapping
    public Recipe createRecipe(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recipe object to save with json format") @RequestBody Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }


    @Operation(summary = "Delete the recipe by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteRecipeById(@Parameter(description = "Id of the recipe") @PathVariable String id) {
        recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update the recipe by id and recipe body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe not found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = RecordNotFoundException.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Error", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<Recipe> updateRecipeById(
            @Parameter(description = "Id of the recipe") @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Recipe object to save with json format") @RequestBody Recipe recipeDetails) {

        Recipe updateRecipe = recipeService.updateRecipe(id, recipeDetails);
        return ResponseEntity.ok(updateRecipe);
    }


}
