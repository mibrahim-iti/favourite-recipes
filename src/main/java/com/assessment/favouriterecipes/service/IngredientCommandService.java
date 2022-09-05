package com.assessment.favouriterecipes.service;

import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.dto.request.IngredientUpdateRequest;

public interface IngredientCommandService {

    void deleteById(final Long ingredientId);

    Long create(IngredientCreateRequest ingredientCreateRequest);

    void update(final Long ingredientId, IngredientUpdateRequest ingredientUpdateRequest);

}
