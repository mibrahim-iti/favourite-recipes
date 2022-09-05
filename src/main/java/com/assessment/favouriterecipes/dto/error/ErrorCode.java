package com.assessment.favouriterecipes.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    VARIABLE_MUST_NOT_NULL("0001", "error.general.not.null", HttpStatus.BAD_REQUEST),
    VARIABLE_MUST_NOT_BLANK("0002", "error.general.not.blank", HttpStatus.BAD_REQUEST),
    RECIPE_NOT_FOUND("1001", "error.recipe.not.found", HttpStatus.NOT_FOUND),
    RECIPE_DUPLICATION_FOUND("1002", "error.recipe.duplication.found", HttpStatus.CONFLICT),

    INGREDIENT_NOT_FOUND("2001", "error.ingredient.not.found", HttpStatus.NOT_FOUND),

    COLLECTION_MUST_NOT_EMPTY("3001", "error.collection.not.empty", HttpStatus.BAD_REQUEST);


    private final String code;

    private final String messageKey;

    private final HttpStatus httpStatus;
}
