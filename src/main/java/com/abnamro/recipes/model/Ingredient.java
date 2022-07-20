package com.abnamro.recipes.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class for Ingredient
 */
@Data
@Builder
@Document
public class Ingredient {
    private String name;
    private String amount;
    boolean isVegetarian;
}
