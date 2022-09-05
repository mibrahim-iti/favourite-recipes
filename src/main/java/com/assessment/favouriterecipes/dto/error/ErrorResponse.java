package com.assessment.favouriterecipes.dto.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private final String code;
    private final String messageKey;
    private final Object value;
}
