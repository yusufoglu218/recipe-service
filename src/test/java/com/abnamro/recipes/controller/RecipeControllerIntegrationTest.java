package com.abnamro.recipes.controller;

import com.abnamro.recipes.constant.TestConstants;
import com.abnamro.recipes.model.Recipe;
import com.abnamro.recipes.service.RecipeServiceImpl;
import com.abnamro.recipes.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecipeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecipeServiceImpl recipeService;

    @LocalServerPort
    private int port;

    private String getRecipeUrl() {
        return "http://localhost:" + port + "/api/v1/recipes/";
    }

    private String recipeIdToDelete;
    private String recipeIdToUpdate;
    private String recipeIdToSave;

    @BeforeAll
    public void createRecipeOnDatabase() {
        recipeIdToDelete = recipeService.saveRecipe(TestUtil.createMockRecipeForDelete()).getId();
        recipeIdToUpdate = recipeService.saveRecipe(TestUtil.createMockRecipeForUpdate()).getId();
    }

    @Test
    public void getRecipeByMultipleParameter_OK() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("instructionsLike", "update");
        parameters.put("ingredientIncluded", "test ingredient");
        parameters.put("ingredientExcluded", "ingredientxx");
        parameters.put("isVegetarian", false);
        parameters.put("numberOfServing", 3);


        ResponseEntity<Recipe[]> responseEntity = restTemplate.getForEntity(
                getRecipeUrl() + "?" +
                        "instructionsLike={instructionsLike}&" +
                        "numberOfServing={numberOfServing}&" +
                        "ingredientIncluded={ingredientIncluded}&" +
                        "ingredientExcluded={ingredientExcluded}&" +
                        "isVegetarian={isVegetarian}",
                Recipe[].class,
                parameters);

        assertTrue(responseEntity.getBody() != null);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));

        Recipe recipe = responseEntity.getBody()[0];

        assertEquals(recipe.getInstructions(), TestConstants.INSTRUCTIONS_TO_UPDATE);
        assertEquals(recipe.getNumberOfServing(), TestConstants.NUMBER_OF_SERVING_TO_UPDATE);

    }

    @Test
    public void getRecipeById_OK() {
        ResponseEntity<Recipe> responseEntity = restTemplate.getForEntity(getRecipeUrl() + recipeIdToUpdate, Recipe.class);

        assertTrue(responseEntity.getBody() != null);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertEquals(responseEntity.getBody().getInstructions(), TestConstants.INSTRUCTIONS_TO_UPDATE);
    }

    @Test
    public void getDeleteById_OK() {
        restTemplate.delete(getRecipeUrl() + recipeIdToDelete);
        ResponseEntity<Recipe> responseEntity = restTemplate.getForEntity(getRecipeUrl() + recipeIdToDelete, Recipe.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getCreateRecipe_OK() {
        ResponseEntity<Recipe> responseEntity = restTemplate.postForEntity(getRecipeUrl(), TestUtil.createMockRecipeForUpdate(), Recipe.class);
        recipeIdToSave = responseEntity.getBody().getId();

        assertTrue(responseEntity.getBody() != null);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertEquals(responseEntity.getBody().getInstructions(), TestConstants.INSTRUCTIONS_TO_UPDATE);
        assertEquals(responseEntity.getBody().getNumberOfServing(), TestConstants.INSTRUCTIONS_TO_UPDATE);

    }

    @Test
    public void getUpdateRecipe_OK() {
        Recipe recipeToUpdate = TestUtil.createMockRecipeForUpdate();

        restTemplate.put(getRecipeUrl() + recipeIdToUpdate, recipeToUpdate);
        ResponseEntity<Recipe> responseEntity = restTemplate.getForEntity(getRecipeUrl() + recipeIdToUpdate, Recipe.class);

        assertTrue(responseEntity.getBody() != null);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));

        Recipe recipeUpdated = responseEntity.getBody();

        assertEquals(recipeUpdated.getInstructions(), recipeToUpdate.getInstructions());
        assertEquals(recipeUpdated.getNumberOfServing(), recipeToUpdate.getNumberOfServing());
    }


}
