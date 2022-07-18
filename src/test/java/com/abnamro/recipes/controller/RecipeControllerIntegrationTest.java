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

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private String getRootUrl() {
        return "http://localhost:" + port;
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
    public void getRecipeById_OK() {
        ResponseEntity<Recipe> responseEntity = restTemplate.getForEntity(getRootUrl() + "/api/v1/recipes/" + recipeIdToUpdate, Recipe.class);
        assertEquals(responseEntity.getBody().getInstructions(), TestConstants.INSTRUCTIONS_TO_UPDATE);
    }

    @Test
    public void getDeleteById_OK() {
        restTemplate.delete(getRootUrl() + "/api/v1/recipes/" + recipeIdToDelete);
        ResponseEntity<Recipe> responseEntity = restTemplate.getForEntity(getRootUrl() + "/api/v1/recipes/" + recipeIdToDelete, Recipe.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void getCreateRecipe_OK() {
        ResponseEntity<Recipe> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/v1/recipes/", TestUtil.createMockRecipeForUpdate(), Recipe.class);
        recipeIdToSave = postResponse.getBody().getId();
        assertEquals(postResponse.getBody().getInstructions(), TestConstants.INSTRUCTIONS_TO_UPDATE);
    }

    @Test
    public void getUpdateRecipe_OK() {
        Recipe recipeToUpdate = TestUtil.createMockRecipeForUpdate();

        restTemplate.put(getRootUrl() + "/api/v1/recipes/" + recipeIdToUpdate, recipeToUpdate);
        ResponseEntity<Recipe> responseEntity = restTemplate.getForEntity(getRootUrl() + "/api/v1/recipes/" + recipeIdToUpdate, Recipe.class);
        Recipe recipeUpdated = responseEntity.getBody();

        assertEquals(recipeUpdated.getInstructions(), recipeToUpdate.getInstructions());
        assertEquals(recipeUpdated.getNumberOfServing(), recipeToUpdate.getNumberOfServing());
    }


}
