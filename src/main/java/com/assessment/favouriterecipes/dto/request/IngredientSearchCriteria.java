package com.assessment.favouriterecipes.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientSearchCriteria {

    private Boolean isInclude;

    private List<String> names;

}
