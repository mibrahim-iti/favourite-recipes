package com.assessment.favouriterecipes.service;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.dto.request.RecipeCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeUpdateRequest;
import com.assessment.favouriterecipes.entity.Ingredient;
import com.assessment.favouriterecipes.entity.Recipe;
import com.assessment.favouriterecipes.exception.FavouriteRecipeException;
import com.assessment.favouriterecipes.mapper.RecipeIngredientMapper;
import com.assessment.favouriterecipes.mapper.RecipeMapper;
import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.mock.RecipeMock;
import com.assessment.favouriterecipes.repository.IngredientRepository;
import com.assessment.favouriterecipes.repository.RecipeIngredientRepository;
import com.assessment.favouriterecipes.repository.RecipeRepository;
import com.assessment.favouriterecipes.service.impl.RecipeServiceImpl;
import com.assessment.favouriterecipes.stub.IngredientStub;
import com.assessment.favouriterecipes.stub.RecipeIngredientStub;
import com.assessment.favouriterecipes.stub.RecipeStub;
import com.assessment.favouriterecipes.stub.UserHelperStub;
import com.assessment.favouriterecipes.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class RecipeCommandServiceTest {

    public static final RecipeMapper recipeMapper = RecipeMapper.INSTANCE;

    @Mock
    private UserHelper userHelper;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;


    private RecipeCommandService recipeCommandService;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);

        recipeCommandService = new RecipeServiceImpl(userHelper, recipeMapper, RecipeIngredientMapper.INSTANCE,
                recipeRepository, ingredientRepository, recipeIngredientRepository);
    }

    @Test
    void givenValidRecipeCreateRequestObject_whenRequestCreateRecipe_thenNewRecipeCreated() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubRecipeSave(recipeRepository);
        RecipeIngredientStub.stubSaveAll(recipeIngredientRepository);
        IngredientStub.stubFindByNameIgnoreCase(ingredientRepository, IngredientMock.INGREDIENT_NAME);
        RecipeStub.stubEmptyFindByNameIgnoreCaseAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_NAME, UserHelperStub.TEST_EMAIL);

        final var createdRecipeId = recipeCommandService.create(RecipeMock.mockRecipeCreateRequest());

        assertNotNull(createdRecipeId);
        verify(recipeIngredientRepository).saveAll(any(List.class));
        verify(recipeRepository).findByNameIgnoreCaseAndCreatedByIgnoreCase(RecipeMock.RECIPE_NAME, UserHelperStub.TEST_EMAIL);
    }

    @Test
    void givenValidRecipeCreateRequestObjectAndPassIngredientsNotInDb_whenRequestCreateRecipe_thenNewRecipeCreated() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubRecipeSave(recipeRepository);
        RecipeIngredientStub.stubSaveAll(recipeIngredientRepository);
        IngredientStub.stubSave(ingredientRepository);
        IngredientStub.stubEmptyFindByNameIgnoreCase(ingredientRepository, IngredientMock.INGREDIENT_NAME);
        RecipeStub.stubEmptyFindByNameIgnoreCaseAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_NAME, UserHelperStub.TEST_EMAIL);

        final var createdRecipeId = recipeCommandService.create(RecipeMock.mockRecipeCreateRequest());

        assertNotNull(createdRecipeId);
        verify(ingredientRepository).save(any(Ingredient.class));
        verify(recipeIngredientRepository).saveAll(any(List.class));
        verify(recipeRepository).findByNameIgnoreCaseAndCreatedByIgnoreCase(RecipeMock.RECIPE_NAME, UserHelperStub.TEST_EMAIL);
    }

    @Test
    void givenNullRecipeCreateRequestObject_whenRequestCreateRecipe_thenExceptionFired() {

        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.create(null),
                "Expected recipeCommandService.create service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeCreateRequest", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_NULL.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenValidRecipeCreateRequestObjectWithNullPropertyValues_whenRequestCreateRecipe_thenExceptionFired() {

        final var recipeCreateRequest = new RecipeCreateRequest();
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.create(recipeCreateRequest),
                "Expected recipeCommandService.create service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeCreateRequest.name", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_NULL.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenValidRecipeCreateRequestObjectWithOnlyNameValueEqualsSpace_whenRequestCreateRecipe_thenExceptionFired() {

        final var recipeCreateRequest = new RecipeCreateRequest();
        recipeCreateRequest.setName(" ");
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.create(recipeCreateRequest),
                "Expected recipeCommandService.create service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeCreateRequest.name", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_BLANK.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenValidRecipeCreateRequestObjectWithNullIngredientList_whenRequestCreateRecipe_thenExceptionFired() {

        final var recipeCreateRequest = RecipeMock.mockRecipeCreateRequestWithoutIngredients();
        var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.create(recipeCreateRequest),
                "Expected recipeCommandService.create service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeCreateRequest.ingredients", thrown.getExtraDetails());
        assertEquals(ErrorCode.COLLECTION_MUST_NOT_EMPTY.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenValidRecipeCreateRequestObjectWithEmptyIngredientList_whenRequestCreateRecipe_thenExceptionFired() {

        final var recipeCreateRequestWithEmptyIngredients = RecipeMock.mockRecipeCreateRequestWithoutIngredients();
        recipeCreateRequestWithEmptyIngredients.setIngredients(new ArrayList<>());
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.create(recipeCreateRequestWithEmptyIngredients),
                "Expected recipeCommandService.create service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeCreateRequest.ingredients", thrown.getExtraDetails());
        assertEquals(ErrorCode.COLLECTION_MUST_NOT_EMPTY.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenValidRecipeCreateRequestObjectWithNameAlreadyExistInDb_whenRequestCreateRecipe_thenExceptionFired() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        IngredientStub.stubFindByNameIgnoreCase(ingredientRepository, IngredientMock.INGREDIENT_NAME);
        RecipeStub.stubFindByNameIgnoreCaseAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_NAME, UserHelperStub.TEST_EMAIL);

        final var recipeCreateRequest = RecipeMock.mockRecipeCreateRequest();
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.create(recipeCreateRequest),
                "Expected recipeCommandService.create service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeName", thrown.getExtraDetails());
        assertEquals(ErrorCode.RECIPE_DUPLICATION_FOUND.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenNullRecipeIdAndNullRecipeUpdateRequestObject_whenRequestUpdateRecipe_thenRecipeUpdated() {

        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.update(null, null),
                "Expected recipeCommandService.update service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeId", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_NULL.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenNullRecipeUpdateRequestObject_whenRequestUpdateRecipe_thenRecipeUpdated() {

        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.update(RecipeMock.RECIPE_ID, null),
                "Expected recipeUpdateRequest.update service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeUpdateRequest", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_NULL.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenRecipeUpdateRequestObjectWithNullProperties_whenRequestUpdateRecipe_thenRecipeUpdated() {

        final var recipeUpdateRequest = new RecipeUpdateRequest();
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.update(RecipeMock.RECIPE_ID, recipeUpdateRequest),
                "Expected recipeUpdateRequest.update service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeUpdateRequest.name", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_NULL.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenRecipeUpdateRequestObjectWithNullPropertiesButNameEqualsSpace_whenRequestUpdateRecipe_thenRecipeUpdated() {

        final var recipeUpdateRequest = new RecipeUpdateRequest();
        recipeUpdateRequest.setName(" ");
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.update(RecipeMock.RECIPE_ID, recipeUpdateRequest),
                "Expected recipeUpdateRequest.update service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeUpdateRequest.name", thrown.getExtraDetails());
        assertEquals(ErrorCode.VARIABLE_MUST_NOT_BLANK.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenRecipeUpdateRequestObjectWithNullIngredients_whenRequestUpdateRecipe_thenRecipeUpdated() {


        final var recipeUpdateRequest = RecipeMock.mockRecipeUpdateRequestWithoutIngredients();
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.update(RecipeMock.RECIPE_ID, recipeUpdateRequest),
                "Expected recipeUpdateRequest.update service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeUpdateRequest.ingredients", thrown.getExtraDetails());
        assertEquals(ErrorCode.COLLECTION_MUST_NOT_EMPTY.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenRecipeUpdateRequestObjectWithEmptyIngredients_whenRequestUpdateRecipe_thenRecipeUpdated() {

        final var recipeUpdateRequest = RecipeMock.mockRecipeUpdateRequestWithoutIngredients();
        recipeUpdateRequest.setIngredients(new ArrayList<>());
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.update(RecipeMock.RECIPE_ID, recipeUpdateRequest),
                "Expected recipeUpdateRequest.update service to throw FavouriteRecipeException, but it didn't");
        assertEquals("recipeUpdateRequest.ingredients", thrown.getExtraDetails());
        assertEquals(ErrorCode.COLLECTION_MUST_NOT_EMPTY.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenValidRecipeUpdateRequestObjectWithNameExistInDb_whenRequestUpdateRecipe_thenRecipeUpdated() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubRecipeFindById(recipeRepository, RecipeMock.mockRecipeWithCreatedByTestUser());
        RecipeStub.stubFindByIdAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_ID, UserHelperStub.TEST_EMAIL);
        RecipeStub.stubFindByNameIgnoreCaseAndIdNot(recipeRepository, RecipeMock.UPDATED_RECIPE_NAME, RecipeMock.RECIPE_ID);

        final var recipeUpdateRequest = RecipeMock.mockRecipeUpdateRequest();
        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.update(RecipeMock.RECIPE_ID, recipeUpdateRequest),
                "Expected recipeUpdateRequest.update service to throw FavouriteRecipeException, but it didn't");

        assertEquals("recipeName", thrown.getExtraDetails());
        assertEquals(ErrorCode.RECIPE_DUPLICATION_FOUND.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void givenValidRecipeUpdateRequestObject_whenRequestUpdateRecipe_thenRecipeUpdated() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        IngredientStub.stubFindByNameIgnoreCase(ingredientRepository, IngredientMock.INGREDIENT_NAME);
        RecipeStub.stubRecipeFindById(recipeRepository, RecipeMock.mockRecipeWithCreatedByTestUser());
        RecipeStub.stubFindByIdAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_ID, UserHelperStub.TEST_EMAIL);
        RecipeStub.stubEmptyFindByNameIgnoreCaseAndIdNot(recipeRepository, RecipeMock.UPDATED_RECIPE_NAME, RecipeMock.RECIPE_ID);

        recipeCommandService.update(RecipeMock.RECIPE_ID, RecipeMock.mockRecipeUpdateRequest());

        verify(userHelper).getLoggedInUsernameValue();
        verify(recipeIngredientRepository).saveAll(any(List.class));
        verify(recipeRepository).findByNameIgnoreCaseAndIdNot(RecipeMock.UPDATED_RECIPE_NAME, RecipeMock.RECIPE_ID);
    }

    @Test
    void givenValidRecipeUpdateRequestObjectWithIngredientsNotInDb_whenRequestUpdateRecipe_thenRecipeUpdated() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        IngredientStub.stubSave(ingredientRepository);
        IngredientStub.stubEmptyFindByNameIgnoreCase(ingredientRepository, IngredientMock.INGREDIENT_NAME);
        RecipeStub.stubRecipeFindById(recipeRepository, RecipeMock.mockRecipeWithCreatedByTestUser());
        RecipeStub.stubFindByIdAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_ID, UserHelperStub.TEST_EMAIL);
        RecipeStub.stubEmptyFindByNameIgnoreCaseAndIdNot(recipeRepository, RecipeMock.UPDATED_RECIPE_NAME, RecipeMock.RECIPE_ID);

        recipeCommandService.update(RecipeMock.RECIPE_ID, RecipeMock.mockRecipeUpdateRequest());

        verify(userHelper).getLoggedInUsernameValue();
        verify(recipeIngredientRepository).saveAll(any(List.class));
        verify(recipeRepository).findByNameIgnoreCaseAndIdNot(RecipeMock.UPDATED_RECIPE_NAME, RecipeMock.RECIPE_ID);
    }

    @Test
    void givenRecipeValidId_whenRequestDeleteRecipe_thenRecipeDeleted() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubRecipeDelete(recipeRepository, RecipeMock.mockRecipe());
        RecipeStub.stubFindByIdAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_ID, UserHelperStub.TEST_EMAIL);

        recipeCommandService.deleteById(RecipeMock.RECIPE_ID);

        verify(recipeRepository).delete(any(Recipe.class));
        verify(recipeRepository).findByIdAndCreatedByIgnoreCase(RecipeMock.RECIPE_ID, UserHelperStub.TEST_EMAIL);
    }

    @Test
    void givenRecipeInValidId_whenRequestDeleteRecipe_thenExceptionFired() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubRecipeFindById(recipeRepository, RecipeMock.mockRecipe());
        RecipeStub.stubEmptyFindByIdAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_ID, UserHelperStub.TEST_EMAIL);

        final var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeCommandService.deleteById(1L),
                "Expected findById() to throw FavouriteRecipeException, but it didn't");

        assertEquals("recipeId", thrown.getExtraDetails());
        assertEquals(ErrorCode.RECIPE_NOT_FOUND.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

}