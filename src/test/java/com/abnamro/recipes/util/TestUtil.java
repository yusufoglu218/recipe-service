package com.abnamro.recipes.util;

import com.abnamro.recipes.constant.TestConstants;
import com.abnamro.recipes.model.Ingredient;
import com.abnamro.recipes.model.Recipe;

import java.util.List;

public class TestUtil {

    public static Recipe createMockRecipeForDelete() {
        Ingredient ingredient = Ingredient.builder().
                name(TestConstants.INGREDIENT_NAME).
                isVegetarian(TestConstants.INGREDIENT_IS_VEGETARIAN_TRUE)
                .build();

        Recipe recipe = Recipe.builder()
                .id(TestConstants.RECIPE_ID_TO_DELETE)
                .instructions(TestConstants.INSTRUCTIONS)
                .numberOfServing(TestConstants.NUMBER_OF_SERVING)
                .ingredients(List.of(ingredient))
                .build();

        return recipe;
    }

    public static Recipe createMockRecipe() {
        Ingredient ingredient = Ingredient.builder()
                .name(TestConstants.INGREDIENT_NAME)
                .isVegetarian(TestConstants.INGREDIENT_IS_VEGETARIAN_FALSE)
                .build();

        Recipe recipe = Recipe.builder()
                .id(TestConstants.RECIPE_ID)
                .instructions(TestConstants.INSTRUCTIONS_TO_UPDATE)
                .numberOfServing(TestConstants.NUMBER_OF_SERVING_TO_UPDATE)
                .ingredients(List.of(ingredient))
                .build();

        return recipe;
    }

}
