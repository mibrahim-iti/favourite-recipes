package com.assessment.favouriterecipes.stub;

import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.mock.RecipeIngredientMock;
import com.assessment.favouriterecipes.mock.RecipeMock;
import com.assessment.favouriterecipes.repository.RecipeIngredientRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

public class RecipeIngredientStub {


    public static void stubSaveAll(RecipeIngredientRepository recipeIngredientRepository) {

        doReturn(List.of(RecipeIngredientMock.mockRecipeIngredient(RecipeMock.mockRecipe(), IngredientMock.mockIngredient())))
                .when(recipeIngredientRepository).saveAll(any(List.class));
    }

    private RecipeIngredientStub() {

        throw new IllegalStateException("Utility class");
    }

}
