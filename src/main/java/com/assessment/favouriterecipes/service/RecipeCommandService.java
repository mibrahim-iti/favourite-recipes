package com.assessment.favouriterecipes.service;

import com.assessment.favouriterecipes.dto.request.RecipeCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeUpdateRequest;

public interface RecipeCommandService {

    void deleteById(final Long recipeId);

    Long create(final RecipeCreateRequest recipeCreateRequest);

    void update(final Long recipeId, final RecipeUpdateRequest recipeUpdateRequest);

}
