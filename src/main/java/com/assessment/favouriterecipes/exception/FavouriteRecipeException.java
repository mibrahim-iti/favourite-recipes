package com.assessment.favouriterecipes.exception;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import lombok.Getter;

@Getter
public class FavouriteRecipeException extends RuntimeException {


    private final ErrorCode errorCode;

    private final String extraDetails;

    public FavouriteRecipeException(ErrorCode errorCode, String extraDetails) {

        this(errorCode, extraDetails, null);
    }

    public FavouriteRecipeException(ErrorCode errorCode, String extraDetails, Throwable throwable) {

        super(throwable);
        this.extraDetails = extraDetails;
        this.errorCode = errorCode;
    }

}
