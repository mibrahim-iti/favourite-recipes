package com.assessment.favouriterecipes.stub;

import com.assessment.favouriterecipes.entity.Ingredient;
import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.repository.IngredientRepository;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;

public class IngredientStub {


    public static void stubIngredientFindById(IngredientRepository ingredientRepository, Ingredient ingredient) {

        doReturn(Optional.of(ingredient)).when(ingredientRepository).findById(ingredient.getId());
    }

    public static void stubEmptyIngredientFindById(IngredientRepository ingredientRepository, Long ingredientId) {

        doReturn(Optional.empty()).when(ingredientRepository).findById(ingredientId);
    }

    public static void stubFindByNameIgnoreCase(IngredientRepository ingredientRepository, String ingredientName) {

        doReturn(Optional.of(IngredientMock.mockIngredient())).when(ingredientRepository).findByNameIgnoreCase(ingredientName);
    }

    public static void stubEmptyFindByNameIgnoreCase(IngredientRepository ingredientRepository, String ingredientName) {

        doReturn(Optional.empty()).when(ingredientRepository).findByNameIgnoreCase(ingredientName);
    }

    public static void stubSave(IngredientRepository ingredientRepository) {

        doReturn(Optional.of(IngredientMock.mockIngredient())).when(ingredientRepository).save(IngredientMock.mockIngredient());
    }


    private IngredientStub() {

        throw new IllegalStateException("Utility class");
    }

}
