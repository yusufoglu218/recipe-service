package com.abnamro.recipes.service;

import com.abnamro.recipes.constant.TestConstants;
import com.abnamro.recipes.exception.ErrorType;
import com.abnamro.recipes.exception.RecordNotFoundException;
import com.abnamro.recipes.model.Recipe;
import com.abnamro.recipes.repository.RecipeRepository;
import com.abnamro.recipes.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Tests for RecipeService
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    private Recipe recipeMock;


    @BeforeAll
    public void createRecipeObjectOnInit() {
        recipeMock = TestUtil.createMockRecipe();
    }


    @Test
    public void getRecipeByMultipleParameter_OK() {
        when(recipeRepository.findByMultipleParameters(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenReturn(List.of(recipeMock));

        List<Recipe> recipeList = recipeService.getRecipeByCriteria(TestConstants.RECIPE_ID, TestConstants.NUMBER_OF_SERVING, TestConstants.INGREDIENT_NAME, TestConstants.INGREDIENT_NAME, TestConstants.INGREDIENT_IS_VEGETARIAN_TRUE);

        Assertions.assertNotNull(recipeList);
        Assertions.assertEquals(recipeList.get(0), recipeMock);
    }

    @Test
    public void getRecipeById_OK() {
        when(recipeRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.ofNullable(recipeMock));

        Recipe recipeFromService = recipeService.getRecipeById(TestConstants.RECIPE_ID);

        Assertions.assertEquals(recipeMock, recipeFromService);
    }

    @Test
    public void getRecipeById_NOT_FOUND() {
        when(recipeRepository.findById(Mockito.anyString())).thenThrow(new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + TestConstants.RECIPE_ID));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            recipeService.getRecipeById(TestConstants.RECIPE_ID);
        });

        assertEquals(ErrorType.RECIPE_NOT_FOUND + TestConstants.RECIPE_ID, thrown.getMessage());
    }

    @Test
    public void deleteRecipeById_NOT_FOUND() {
        when(recipeRepository.findById(Mockito.anyString())).thenThrow(new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + TestConstants.RECIPE_ID));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            recipeService.deleteRecipeById(TestConstants.RECIPE_ID);
        });

        assertEquals(ErrorType.RECIPE_NOT_FOUND + TestConstants.RECIPE_ID, thrown.getMessage());
    }


    @Test
    public void updateRecipe_OK() {
        when(recipeRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.ofNullable(recipeMock));
        when(recipeRepository.save(Mockito.any())).thenReturn(recipeMock);

        Recipe recipeFromService = recipeService.updateRecipe(TestConstants.RECIPE_ID, recipeMock);

        Assertions.assertEquals(recipeMock, recipeFromService);
    }

    @Test
    public void updateRecipe_NOT_FOUND() {
        when(recipeRepository.findById(Mockito.anyString())).thenThrow(new RecordNotFoundException(ErrorType.RECIPE_NOT_FOUND + TestConstants.RECIPE_ID));

        RecordNotFoundException thrown = Assertions.assertThrows(RecordNotFoundException.class, () -> {
            recipeService.updateRecipe(TestConstants.RECIPE_ID, recipeMock);
        });

        assertEquals(ErrorType.RECIPE_NOT_FOUND + TestConstants.RECIPE_ID, thrown.getMessage());
    }

    @Test
    public void createRecipe_OK() {
        when(recipeRepository.save(Mockito.any())).thenReturn(recipeMock);

        Recipe recipeFromService = recipeService.saveRecipe(recipeMock);

        Assertions.assertEquals(recipeMock, recipeFromService);
    }

}
