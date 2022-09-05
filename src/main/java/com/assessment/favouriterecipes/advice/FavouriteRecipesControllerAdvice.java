package com.assessment.favouriterecipes.advice;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.dto.error.ErrorResponse;
import com.assessment.favouriterecipes.exception.FavouriteRecipeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class FavouriteRecipesControllerAdvice {

    @ExceptionHandler(value = FavouriteRecipeException.class)
    protected ResponseEntity<Object> handleConflict(FavouriteRecipeException ex) {

        HttpStatus correspondingHttpStatusCode = ex.getErrorCode().getHttpStatus();
        return ResponseEntity.status(correspondingHttpStatusCode).body(toUnifiedNoneOkResponseBody(ex));
    }

    private ErrorResponse toUnifiedNoneOkResponseBody(FavouriteRecipeException ex) {

        Object value = Objects.isNull(ex.getMessage()) ? ex.getExtraDetails() : ex.getMessage();
        ErrorCode errorCode = ex.getErrorCode();
        return ErrorResponse.builder().code(errorCode.getCode())
                .messageKey(errorCode.getMessageKey()).value(value).build();
    }

}
