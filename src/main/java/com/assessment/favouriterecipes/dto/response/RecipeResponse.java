package com.assessment.favouriterecipes.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RecipeResponse {

    private Long id;

    private String name;

    private Boolean isVegetarian;

    private Integer servingCapacity;

    private String instructions;

    private List<RecipeIngredientResponse> ingredients;

}
