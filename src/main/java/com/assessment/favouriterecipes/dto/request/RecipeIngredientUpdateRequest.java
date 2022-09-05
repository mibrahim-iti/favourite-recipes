package com.assessment.favouriterecipes.dto.request;

import com.assessment.favouriterecipes.constant.JavaxValidationMessage;
import com.assessment.favouriterecipes.enums.WeightUnit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RecipeIngredientUpdateRequest {

    @NotNull(message = JavaxValidationMessage.INGREDIENT_NAME_NOT_NULL)
    @NotBlank(message = JavaxValidationMessage.INGREDIENT_NAME_NOT_EMPTY)
    private String name;

    @NotNull(message = "")
    private WeightUnit weightUnit;

    @NotNull(message = "")
    private Integer weightQuantity;

}
