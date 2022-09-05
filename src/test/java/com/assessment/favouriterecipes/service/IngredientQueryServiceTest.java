package com.assessment.favouriterecipes.service;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
class IngredientQueryServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    private IngredientQueryService ingredientService;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(IngredientMapper.INSTANCE, ingredientRepository);
    }

    @Test
    void givenIngredientValidId_WhenRequestIngredient_ThenValidResultBack() {

        IngredientStub.stubIngredientFindById(ingredientRepository, IngredientMock.mockIngredient());

        final var ingredientResponse = ingredientService.findById(IngredientMock.INGREDIENT_ID);

        assertThat(ingredientResponse).isNotNull();
        assertThat(ingredientResponse.getId()).isEqualTo(IngredientMock.INGREDIENT_ID);
        assertThat(ingredientResponse.getName()).isEqualTo(IngredientMock.INGREDIENT_NAME);
    }

    @Test
    void givenIngredientInValidId_WhenRequestIngredient_ThenExceptionFired() {

        IngredientStub.stubEmptyIngredientFindById(ingredientRepository, IngredientMock.INGREDIENT_ID);

        var thrown = assertThrows(FavouriteRecipeException.class, () -> ingredientService.findById(1L),
                "Expected findById() to throw FavouriteRecipeException, but it didn't");

        assertEquals("ingredientId", thrown.getExtraDetails());
        assertEquals(ErrorCode.INGREDIENT_NOT_FOUND.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

}