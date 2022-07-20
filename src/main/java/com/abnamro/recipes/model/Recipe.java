package com.abnamro.recipes.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Class for Recipe
 */
@Data
@Builder
@Document
public class Recipe {
    @Id
    private String id;
    private String instructions;
    private int numberOfServing;
    private List<Ingredient> ingredients;
}
