package com.assessment.favouriterecipes.util;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.exception.FavouriteRecipeException;
import lombok.Generated;

import java.util.Collection;

import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

public class Validator {

    @Generated
    private Validator() {

        throw new IllegalStateException("Utility class");
    }

    public static void validateNotNull(final Object object, final String variableName) {

        if (object == null) {
            throw new FavouriteRecipeException(ErrorCode.VARIABLE_MUST_NOT_NULL, variableName);
        }
    }

    public static void validateNotBlank(final String str, final String variableName) {

        validateNotNull(str, variableName);
        if (!hasText(str)) {
            throw new FavouriteRecipeException(ErrorCode.VARIABLE_MUST_NOT_BLANK, variableName);
        }
    }

    public static void validateNotEmpty(final Collection<?> collection, final String variableName) {

        if (isEmpty(collection)) {
            throw new FavouriteRecipeException(ErrorCode.COLLECTION_MUST_NOT_EMPTY, variableName);
        }
    }

}
