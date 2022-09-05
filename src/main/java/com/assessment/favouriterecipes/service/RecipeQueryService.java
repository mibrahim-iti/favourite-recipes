package com.assessment.favouriterecipes.service;

import com.assessment.favouriterecipes.dto.request.RecipesSearchCriteria;
import com.assessment.favouriterecipes.dto.response.PaginationResponse;
import com.assessment.favouriterecipes.dto.response.RecipeResponse;

public interface RecipeQueryService {

    RecipeResponse findById(final Long recipeId);

    PaginationResponse<RecipeResponse> listRecipes(RecipesSearchCriteria recipesSearchCriteria);

}
