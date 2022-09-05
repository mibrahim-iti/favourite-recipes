package com.assessment.favouriterecipes.service;

import com.assessment.favouriterecipes.dto.response.IngredientResponse;

public interface IngredientQueryService {

    IngredientResponse findById(final Long ingredientId);

}
