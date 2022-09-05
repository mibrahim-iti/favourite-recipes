package com.assessment.favouriterecipes.dto.request;

import com.assessment.favouriterecipes.constant.JavaxValidationMessage;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class IngredientUpdateRequest {

    @NotNull(message = JavaxValidationMessage.INGREDIENT_NAME_NOT_NULL)
    @NotEmpty(message = JavaxValidationMessage.INGREDIENT_NAME_NOT_EMPTY)
    private String name;

}
