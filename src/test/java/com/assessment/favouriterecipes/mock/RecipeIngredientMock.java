package com.assessment.favouriterecipes.mock;

import com.assessment.favouriterecipes.dto.request.RecipeIngredientCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeIngredientUpdateRequest;
import com.assessment.favouriterecipes.entity.Ingredient;
import com.assessment.favouriterecipes.entity.Recipe;
import com.assessment.favouriterecipes.entity.RecipeIngredient;
import com.assessment.favouriterecipes.enums.WeightUnit;
import com.assessment.favouriterecipes.stub.UserHelperStub;

public class RecipeIngredientMock {


    public static final WeightUnit WEIGHT_UNIT = WeightUnit.GRAM;

    public static final int WEIGHT_QUANTITY = 5;

    private RecipeIngredientMock() {

        throw new IllegalStateException("Utility class");
    }

    public static RecipeIngredientCreateRequest mockRecipeIngredientCreateRequest() {

        final var recipeIngredientCreateRequest = new RecipeIngredientCreateRequest();

        recipeIngredientCreateRequest.setName(IngredientMock.INGREDIENT_NAME);
        recipeIngredientCreateRequest.setWeightUnit(WEIGHT_UNIT);
        recipeIngredientCreateRequest.setWeightQuantity(WEIGHT_QUANTITY);

        return recipeIngredientCreateRequest;
    }

    public static RecipeIngredientUpdateRequest mockRecipeIngredientUpdateRequest() {

        final var recipeIngredientUpdateRequest = new RecipeIngredientUpdateRequest();

        recipeIngredientUpdateRequest.setName(IngredientMock.UPDATED_INGREDIENT_NAME);
        recipeIngredientUpdateRequest.setWeightUnit(WEIGHT_UNIT);
        recipeIngredientUpdateRequest.setWeightQuantity(WEIGHT_QUANTITY);

        return recipeIngredientUpdateRequest;
    }

    public static RecipeIngredient mockRecipeIngredient(Recipe recipe, Ingredient ingredient) {

        return createNewRecipeIngredient(recipe, ingredient, WEIGHT_UNIT, WEIGHT_QUANTITY);
    }

    public static RecipeIngredient createNewRecipeIngredient(Recipe recipe, Ingredient ingredient, WeightUnit weightUnit, Integer weightQuantity) {

        final var recipeIngredient = new RecipeIngredient();

        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setWeightUnit(weightUnit);
        recipeIngredient.setWeightQuantity(weightQuantity);
        recipeIngredient.setCreatedBy(UserHelperStub.TEST_EMAIL);

        return recipeIngredient;
    }


}
