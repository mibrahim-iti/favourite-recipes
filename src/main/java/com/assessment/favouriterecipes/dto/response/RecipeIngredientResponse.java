package com.assessment.favouriterecipes.dto.response;

import com.assessment.favouriterecipes.enums.WeightUnit;
import lombok.Data;

@Data
public class RecipeIngredientResponse {

    private String name;

    private WeightUnit weightUnit;

    private Integer weightQuantity;

}
