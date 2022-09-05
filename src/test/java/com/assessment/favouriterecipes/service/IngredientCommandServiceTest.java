package com.assessment.favouriterecipes.service;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.entity.Ingredient;
import com.assessment.favouriterecipes.exception.FavouriteRecipeException;
import com.assessment.favouriterecipes.mapper.IngredientMapper;
import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.repository.IngredientRepository;
import com.assessment.favouriterecipes.service.impl.IngredientServiceImpl;
import com.assessment.favouriterecipes.stub.IngredientStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class IngredientCommandServiceTest {


    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientCommandService ingredientService;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(IngredientMapper.INSTANCE, ingredientRepository);
    }

    @Test
    void testCreate() {

        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(IngredientMock.mockIngredient());

        ingredientService.create(IngredientMock.mockIngredientCreateRequest());

        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void testInvalidCreate() {

        var thrown = assertThrows(FavouriteRecipeException.class, () -> ingredientService.create(null),
                "Expected findById() to throw FavouriteRecipeException, but it didn't");

        assertEquals("ingredientCreateRequest", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_NULL.getMessageKey(), thrown.getErrorCode().getMessageKey());

        final var ingredientCreateRequest = new IngredientCreateRequest();
        thrown = assertThrows(FavouriteRecipeException.class, () -> ingredientService.create(ingredientCreateRequest),
                "Expected findById() to throw FavouriteRecipeException, but it didn't");

        assertEquals("ingredientCreateRequest.name", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_NULL.getMessageKey(), thrown.getErrorCode().getMessageKey());

        ingredientCreateRequest.setName(" ");
        thrown = assertThrows(FavouriteRecipeException.class, () -> ingredientService.create(ingredientCreateRequest),
                "Expected findById() to throw FavouriteRecipeException, but it didn't");

        assertEquals("ingredientCreateRequest.name", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_BLANK.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void testUpdate() {

        final var ingredient = IngredientMock.mockIngredient();

        IngredientStub.stubIngredientFindById(ingredientRepository, ingredient);

        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        ingredientService.update(IngredientMock.INGREDIENT_ID, IngredientMock.mockIngredientUpdateRequest());

        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void testDelete() {

        doNothing().when(ingredientRepository).deleteById(IngredientMock.INGREDIENT_ID);

        ingredientService.deleteById(IngredientMock.INGREDIENT_ID);

        verify(ingredientRepository).deleteById(IngredientMock.INGREDIENT_ID);
    }

}