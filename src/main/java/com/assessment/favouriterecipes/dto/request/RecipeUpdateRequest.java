package com.assessment.favouriterecipes.dto.request;

import com.assessment.favouriterecipes.constant.JavaxValidationMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RecipeUpdateRequest {

    @NotNull(message = JavaxValidationMessage.RECIPE_NAME_NOT_NULL)
    @NotBlank(message = JavaxValidationMessage.RECIPE_NAME_NOT_EMPTY)
    private String name;

    private Boolean isVegetarian = Boolean.FALSE;

    private Integer servingCapacity = 1;

    private String instructions;

    @NotNull(message = JavaxValidationMessage.INGREDIENT_LIST_NOT_NULL)
    @NotEmpty(message = JavaxValidationMessage.INGREDIENT_LIST_NOT_EMPTY)
    private List<RecipeIngredientUpdateRequest> ingredients;


}
