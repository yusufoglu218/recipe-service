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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

    private Recipe recipeMock;


    @BeforeAll
    public void createRecipeObjectOnInit() {
        recipeMock = TestUtil.createMockRecipe();
        recipeService.saveRecipe(TestUtil.createMockRecipeForDelete());
        recipeService.saveRecipe(TestUtil.createMockRecipe());
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
        assertEquals(responseEntity.getBody()[0], recipeMock);
    }

    @Test
    public void getRecipeByMultipleParameter_NOT_FOUND() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("instructionsLike", "update");
        parameters.put("ingredientIncluded", "test ingredient");
        parameters.put("isVegetarian", true);
        parameters.put("numberOfServing", 3);

        ResponseEntity<Recipe[]> responseEntity = restTemplate.getForEntity(
                getRecipeUrl() + "?" +
                        "instructionsLike={instructionsLike}&" +
                        "numberOfServing={numberOfServing}&" +
                        "ingredientIncluded={ingredientIncluded}&" +
                        "isVegetarian={isVegetarian}",
                Recipe[].class,
                parameters);

        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertEquals(responseEntity.getBody().length, 0);
    }

    @Test
    public void getRecipeById_OK() {
        ResponseEntity<Recipe> responseEntity = restTemplate.getForEntity(getRecipeUrl() + TestConstants.RECIPE_ID, Recipe.class);

        assertTrue(responseEntity.getBody() != null);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertEquals(responseEntity.getBody(), recipeMock);
    }

    @Test
    public void getRecipeById_NOT_FOUND() {
        ResponseEntity<Recipe> responseEntity = restTemplate.getForEntity(getRecipeUrl() + "111", Recipe.class);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    public void deleteRecipeById_OK() {
        ResponseEntity<HttpStatus> responseEntity = restTemplate.exchange(getRecipeUrl() + "{id}", HttpMethod.DELETE, null, HttpStatus.class, TestConstants.RECIPE_ID_TO_DELETE);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteRecipeById_NOT_FOUND() {
        ResponseEntity<Object> responseEntity = restTemplate.exchange(getRecipeUrl() + "{id}", HttpMethod.DELETE, null, Object.class, "111");
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

    @Test
    public void createRecipe_OK() {
        ResponseEntity<Recipe> responseEntity = restTemplate.postForEntity(getRecipeUrl(), TestUtil.createMockRecipe(), Recipe.class);

        assertTrue(responseEntity.getBody() != null);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertEquals(responseEntity.getBody(), recipeMock);
    }

    @Test
    public void updateRecipe_OK() {
        HttpEntity<Recipe> requestEntity = new HttpEntity<>(recipeMock);
        ResponseEntity<Recipe> responseEntity = restTemplate.exchange(getRecipeUrl() + TestConstants.RECIPE_ID, HttpMethod.PUT, requestEntity, Recipe.class);

        assertTrue(responseEntity.getBody() != null);
        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.OK));
        assertEquals(responseEntity.getBody(), recipeMock);
    }

    @Test
    public void updateRecipe_NOT_FOUND() {
        HttpEntity<Recipe> requestEntity = new HttpEntity<>(recipeMock);
        ResponseEntity<Recipe> responseEntity = restTemplate.exchange(getRecipeUrl() + "111", HttpMethod.PUT, requestEntity, Recipe.class);

        assertTrue(responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND));
    }

}
