package com.abnamro.recipes.util;

import com.abnamro.recipes.constant.TestConstants;
import com.abnamro.recipes.model.Ingredient;
import com.abnamro.recipes.model.Recipe;

import java.util.List;

public class TestUtil {

    public static Recipe createMockRecipeForDelete() {
        Ingredient ingredient = Ingredient.builder().
                name(TestConstants.INGREDIENT_NAME)
                .amount(TestConstants.INGREDIENT_AMOUNT)
                .isVegetarian(TestConstants.INGREDIENT_IS_VEGETARIAN_TRUE)
                .build();

        Recipe recipe = Recipe.builder()
                .id(TestConstants.RECIPE_ID_TO_DELETE)
                .name(TestConstants.RECIPE_NAME_TO_DELETE)
                .instructions(TestConstants.INSTRUCTIONS_TO_DELETE)
                .numberOfServing(TestConstants.NUMBER_OF_SERVING_TO_DELETE)
                .ingredients(List.of(ingredient))
                .build();

        return recipe;
    }

    public static Recipe createMockRecipe() {
        Ingredient ingredient = Ingredient.builder()
                .name(TestConstants.INGREDIENT_NAME)
                .amount(TestConstants.INGREDIENT_AMOUNT)
                .isVegetarian(TestConstants.INGREDIENT_IS_VEGETARIAN_FALSE)
                .build();

        Recipe recipe = Recipe.builder()
                .id(TestConstants.RECIPE_ID)
                .name(TestConstants.RECIPE_NAME)
                .instructions(TestConstants.INSTRUCTIONS)
                .numberOfServing(TestConstants.NUMBER_OF_SERVING)
                .ingredients(List.of(ingredient))
                .build();

        return recipe;
    }

}
