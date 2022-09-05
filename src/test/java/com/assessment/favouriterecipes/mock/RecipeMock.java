package com.assessment.favouriterecipes.mock;

import com.assessment.favouriterecipes.dto.request.RecipeCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeUpdateRequest;
import com.assessment.favouriterecipes.dto.response.RecipeResponse;
import com.assessment.favouriterecipes.entity.Recipe;
import com.assessment.favouriterecipes.enums.WeightUnit;
import com.assessment.favouriterecipes.stub.UserHelperStub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecipeMock {

    public static final Long RECIPE_ID = 1L;

    public static final String RECIPE_NAME = "Recipe Test";

    public static final String UPDATED_RECIPE_NAME = "Updated Recipe Test";

    public static final String RECIPE_INSTRUCTIONS = "Recipe Test instructions";

    public static final String UPDATED_RECIPE_INSTRUCTIONS = "Updated Recipe Test instructions";

    public static final int SERVING_CAPACITY = 5;

    public static final int UPDATED_SERVING_CAPACITY = 7;

    public static Optional<Recipe> mockOptionalRecipe() {

        return Optional.of(mockRecipeWithCreatedByTestUser());
    }

    public static Recipe mockRecipe() {

        final var recipe = mockRecipeWithoutId();

        recipe.setId(RECIPE_ID);

        return recipe;
    }

    public static Recipe mockRecipeWithCreatedByTestUser() {

        final var recipe = mockRecipe();

        recipe.setCreatedBy(UserHelperStub.TEST_EMAIL);

        return recipe;
    }

    public static Recipe mockRecipeWithoutId() {

        final var recipe = mockRecipeWithoutIdAndWithoutIngredients();

        recipe.setIngredients(List.of(RecipeIngredientMock.mockRecipeIngredient(recipe, IngredientMock.mockIngredient())));

        return recipe;
    }

    public static Recipe mockRecipeWithoutIdAndWithoutIngredients() {

        return createNewRecipe(RECIPE_NAME, SERVING_CAPACITY, Boolean.TRUE, RECIPE_INSTRUCTIONS);
    }

    public static RecipeCreateRequest mockRecipeCreateRequestWithoutIngredients() {

        final var recipeCreateRequest = new RecipeCreateRequest();

        recipeCreateRequest.setName(RECIPE_NAME);
        recipeCreateRequest.setInstructions(RECIPE_INSTRUCTIONS);
        recipeCreateRequest.setServingCapacity(SERVING_CAPACITY);
        recipeCreateRequest.setIsVegetarian(Boolean.TRUE);

        return recipeCreateRequest;
    }

    public static RecipeCreateRequest mockRecipeCreateRequest() {

        final var recipeCreateRequest = mockRecipeCreateRequestWithoutIngredients();

        recipeCreateRequest.setIngredients(List.of(RecipeIngredientMock.mockRecipeIngredientCreateRequest()));

        return recipeCreateRequest;
    }

    public static RecipeUpdateRequest mockRecipeUpdateRequest() {

        final var recipeUpdateRequest = mockRecipeUpdateRequestWithoutIngredients();

        recipeUpdateRequest.setIngredients(List.of(RecipeIngredientMock.mockRecipeIngredientUpdateRequest()));

        return recipeUpdateRequest;
    }

    public static RecipeUpdateRequest mockRecipeUpdateRequestWithoutIngredients() {

        final var recipeUpdateRequest = new RecipeUpdateRequest();

        recipeUpdateRequest.setName(UPDATED_RECIPE_NAME);
        recipeUpdateRequest.setInstructions(UPDATED_RECIPE_INSTRUCTIONS);
        recipeUpdateRequest.setServingCapacity(UPDATED_SERVING_CAPACITY);
        recipeUpdateRequest.setIsVegetarian(Boolean.FALSE);

        return recipeUpdateRequest;
    }

    public static List<Recipe> mockListRecipes() {

        final var recipes = new ArrayList<Recipe>();

        for (int i = 0; i < 10; i++) {
            final var recipe = createNewRecipe(RECIPE_NAME + i, SERVING_CAPACITY, Boolean.TRUE, RECIPE_INSTRUCTIONS + i);
            final var ingredient = IngredientMock.createNewIngredient(IngredientMock.INGREDIENT_NAME + i);
            recipe.setIngredients(List.of(RecipeIngredientMock.createNewRecipeIngredient(recipe, ingredient, WeightUnit.GRAM, i)));
            recipes.add(recipe);
        }

        return recipes;
    }

    public static List<RecipeResponse> mockListRecipeResponses() {

        final var recipes = new ArrayList<RecipeResponse>();

        for (int i = 0; i < 10; i++) {
            final var recipe = createNewRecipeResponse(RECIPE_NAME + i, SERVING_CAPACITY, Boolean.TRUE, RECIPE_INSTRUCTIONS + i);

            recipes.add(recipe);
        }

        return recipes;
    }

    private static Recipe createNewRecipe(String recipeName, Integer servingCapacity, Boolean isVegetarian, String instructions) {

        final var recipe = new Recipe();

        recipe.setName(recipeName);
        recipe.setCreatedBy(UserHelperStub.TEST_EMAIL);
        recipe.setServingCapacity(servingCapacity);
        recipe.setIsVegetarian(isVegetarian);
        recipe.setInstructions(instructions);

        return recipe;
    }

    private static RecipeResponse createNewRecipeResponse(String recipeName, Integer servingCapacity, Boolean isVegetarian, String instructions) {

        final var recipe = new RecipeResponse();

        recipe.setName(recipeName);
        recipe.setServingCapacity(servingCapacity);
        recipe.setIsVegetarian(isVegetarian);
        recipe.setInstructions(instructions);

        return recipe;
    }

    private RecipeMock() {

        throw new IllegalStateException("Utility class");
    }

}
