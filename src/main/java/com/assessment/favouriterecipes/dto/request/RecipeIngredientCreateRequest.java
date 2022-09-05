package com.assessment.favouriterecipes.dto.request;

import com.assessment.favouriterecipes.constant.JavaxValidationMessage;
import com.assessment.favouriterecipes.enums.WeightUnit;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RecipeIngredientCreateRequest {

    @NotNull(message = JavaxValidationMessage.INGREDIENT_NAME_NOT_NULL)
    @NotBlank(message = JavaxValidationMessage.INGREDIENT_NAME_NOT_EMPTY)
    private String name;

    @NotNull(message = JavaxValidationMessage.INGREDIENT_WEIGHT_UNIT_NOT_NULL)
    private WeightUnit weightUnit;

    @NotNull(message = JavaxValidationMessage.INGREDIENT_WEIGHT_QUANTITY_NOT_NULL)
    private Integer weightQuantity;

}
