package com.assessment.favouriterecipes.dto.request;

import com.assessment.favouriterecipes.specification.PageableCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RecipesSearchCriteria extends PageableCriteria {

    private String instructions;

    private Boolean isVegetarian;

    private Integer servingCapacity;

    private IngredientSearchCriteria ingredients;

}
