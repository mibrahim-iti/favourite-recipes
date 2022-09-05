package com.assessment.favouriterecipes.mock;

import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.dto.request.IngredientUpdateRequest;
import com.assessment.favouriterecipes.entity.Ingredient;
import com.assessment.favouriterecipes.stub.UserHelperStub;

public class IngredientMock {

    public static final Long INGREDIENT_ID = 1L;

    public static final String INGREDIENT_NAME = "Ingredient Test";
    public static final String UPDATED_INGREDIENT_NAME = "Updated Ingredient Test";

    public static Ingredient mockIngredient() {

        final var ingredient = mockIngredientWithoutId();
        ingredient.setId(INGREDIENT_ID);

        return ingredient;
    }

    public static Ingredient mockIngredientWithoutId() {

        return createNewIngredient(INGREDIENT_NAME);
    }

    public static IngredientUpdateRequest mockIngredientUpdateRequest() {

        final var ingredientUpdateRequest = new IngredientUpdateRequest();
        ingredientUpdateRequest.setName(INGREDIENT_NAME);

        return ingredientUpdateRequest;
    }

    public static IngredientCreateRequest mockIngredientCreateRequest() {

        final var ingredientCreateRequest = new IngredientCreateRequest();
        ingredientCreateRequest.setName(INGREDIENT_NAME);

        return ingredientCreateRequest;
    }

    public static Ingredient createNewIngredient(String ingredientName) {

        final var ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        ingredient.setCreatedBy(UserHelperStub.TEST_EMAIL);

        return ingredient;
    }

    private IngredientMock() {

        throw new IllegalStateException("Utility class");
    }

}
