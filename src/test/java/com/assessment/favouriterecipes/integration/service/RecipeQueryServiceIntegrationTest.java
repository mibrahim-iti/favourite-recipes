package com.assessment.favouriterecipes.integration.service;

import com.assessment.favouriterecipes.dto.request.IngredientSearchCriteria;
import com.assessment.favouriterecipes.dto.request.RecipesSearchCriteria;
import com.assessment.favouriterecipes.integration.common.IntegrationServiceAbstractTest;
import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.mock.RecipeMock;
import com.assessment.favouriterecipes.service.RecipeCommandService;
import com.assessment.favouriterecipes.service.RecipeQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RecipeQueryServiceIntegrationTest extends IntegrationServiceAbstractTest {

    @Autowired
    private RecipeQueryService recipeQueryService;

    @Autowired
    private RecipeCommandService recipeCommandService;

    @Test
    void testSuccessfullyFindByIdResult() {

        final var recipeId = createOneRecipeForTesting();

        final var recipeResponse = recipeQueryService.findById(recipeId);

        assertThat(recipeResponse.getId()).isNotNull();
    }

    @Test
    void givenInvalidRecipeId_when() {

        final var recipeId = createOneRecipeForTesting();

        final var recipeResponse = recipeQueryService.findById(recipeId);

        assertThat(recipeResponse.getId()).isNotNull();
    }

    @Test
    void testSuccessfullyListingBySearchCriteria() {

        createOneRecipeForTesting();

        final var criteriaBuilder = RecipesSearchCriteria.builder();
        criteriaBuilder.direction(Sort.Direction.DESC).page(0).size(10);

        whenRecipeSearchCriteriaPassedWithNoSearchProperties(criteriaBuilder.build());

        whenRecipeSearchCriteriaPassedWithOnlyIngredientsIncluded(criteriaBuilder.ingredients(buildIngredientSearchCriteria(Boolean.TRUE)).build());

        whenRecipeSearchCriteriaPassedWithOnlyIsVegetarianTrueValue(criteriaBuilder.isVegetarian(Boolean.TRUE).build());

        whenRecipeSearchCriteriaPassedWithOnlyServingCapacityValue(criteriaBuilder.servingCapacity(RecipeMock.SERVING_CAPACITY).build());

        whenRecipeSearchCriteriaPassedWithOnlyInstructionsValue(criteriaBuilder.instructions(RecipeMock.RECIPE_INSTRUCTIONS).build());

        final var recipesSearchCriteria = criteriaBuilder
                .servingCapacity(RecipeMock.SERVING_CAPACITY)
                .instructions(RecipeMock.RECIPE_INSTRUCTIONS).build();
        whenRecipeSearchCriteriaPassedWithMixingValues(recipesSearchCriteria);

        recipesSearchCriteria.setIsVegetarian(Boolean.TRUE);
        whenRecipeSearchCriteriaPassedWithMixingValues(recipesSearchCriteria);

        recipesSearchCriteria.setIngredients(buildIngredientSearchCriteria(Boolean.TRUE));
        whenRecipeSearchCriteriaPassedWithMixingValues(recipesSearchCriteria);

    }

    private static IngredientSearchCriteria buildIngredientSearchCriteria(boolean isIncluded) {

        final var ingredientSearchCriteria = IngredientSearchCriteria.builder()
                .isInclude(isIncluded)
                .names(List.of(IngredientMock.INGREDIENT_NAME)).build();

        return ingredientSearchCriteria;
    }

    private void validateSearchResult(final RecipesSearchCriteria recipesSearchCriteria) {

        final var recipeResponsePaginationResponse = recipeQueryService.listRecipes(recipesSearchCriteria);

        assertThat(recipeResponsePaginationResponse).isNotNull();
        assertEquals(1, recipeResponsePaginationResponse.getTotalPages());
        assertEquals(1, recipeResponsePaginationResponse.getTotalElements());
        assertEquals(1, recipeResponsePaginationResponse.getData().size());
    }

    private void whenRecipeSearchCriteriaPassedWithNoSearchProperties(final RecipesSearchCriteria recipesSearchCriteria) {

        validateSearchResult(recipesSearchCriteria);
    }

    private void whenRecipeSearchCriteriaPassedWithOnlyIngredientsIncluded(final RecipesSearchCriteria recipesSearchCriteria) {

        validateSearchResult(recipesSearchCriteria);
    }

    private void whenRecipeSearchCriteriaPassedWithOnlyIsVegetarianTrueValue(final RecipesSearchCriteria recipesSearchCriteria) {

        validateSearchResult(recipesSearchCriteria);
    }

    private void whenRecipeSearchCriteriaPassedWithOnlyServingCapacityValue(final RecipesSearchCriteria recipesSearchCriteria) {

        validateSearchResult(recipesSearchCriteria);
    }

    private void whenRecipeSearchCriteriaPassedWithOnlyInstructionsValue(final RecipesSearchCriteria recipesSearchCriteria) {

        validateSearchResult(recipesSearchCriteria);
    }

    private void whenRecipeSearchCriteriaPassedWithMixingValues(final RecipesSearchCriteria recipesSearchCriteria) {

        validateSearchResult(recipesSearchCriteria);
    }


    private Long createOneRecipeForTesting() {

        final var recipeCreateRequest = RecipeMock.mockRecipeCreateRequest();

        final var recipeId = recipeCommandService.create(recipeCreateRequest);

        assertThat(recipeId).isNotNull();

        return recipeId;
    }

}
