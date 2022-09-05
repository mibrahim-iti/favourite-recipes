package com.assessment.favouriterecipes.service;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.dto.request.IngredientSearchCriteria;
import com.assessment.favouriterecipes.dto.request.RecipesSearchCriteria;
import com.assessment.favouriterecipes.exception.FavouriteRecipeException;
import com.assessment.favouriterecipes.mapper.RecipeIngredientMapper;
import com.assessment.favouriterecipes.mapper.RecipeMapper;
import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.mock.RecipeMock;
import com.assessment.favouriterecipes.repository.IngredientRepository;
import com.assessment.favouriterecipes.repository.RecipeIngredientRepository;
import com.assessment.favouriterecipes.repository.RecipeRepository;
import com.assessment.favouriterecipes.service.impl.RecipeServiceImpl;
import com.assessment.favouriterecipes.stub.RecipeStub;
import com.assessment.favouriterecipes.stub.UserHelperStub;
import com.assessment.favouriterecipes.util.UserHelper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
class RecipeQueryServiceTest {

    @Mock
    private UserHelper userHelper;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    private RecipeQueryService recipeQueryService;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);

        var recipeMapper = RecipeMapper.INSTANCE;
        var recipeIngredientMapper = RecipeIngredientMapper.INSTANCE;
        ReflectionTestUtils.setField(recipeMapper, "recipeIngredientMapper", recipeIngredientMapper);

        recipeQueryService = new RecipeServiceImpl(userHelper, recipeMapper, recipeIngredientMapper,
                recipeRepository, ingredientRepository, recipeIngredientRepository);
    }

    @Test
    void givenRecipeValidId_WhenRequestRecipe_ThenValidResultBack() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubRecipeFindById(recipeRepository, RecipeMock.mockRecipe());
        RecipeStub.stubFindByIdAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_ID, UserHelperStub.TEST_EMAIL);

        final var ingredientResponse = recipeQueryService.findById(RecipeMock.RECIPE_ID);

        assertThat(ingredientResponse).isNotNull();
        assertThat(ingredientResponse.getId()).isEqualTo(RecipeMock.RECIPE_ID);
        assertThat(ingredientResponse.getIsVegetarian()).isEqualTo(Boolean.TRUE);
        assertThat(ingredientResponse.getInstructions()).isEqualTo(RecipeMock.RECIPE_INSTRUCTIONS);
        assertThat(ingredientResponse.getIngredients()).hasSize(1);
    }

    @Test
    void givenRecipeInValidId_WhenRequestRecipe_ThenExceptionFired() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubRecipeFindById(recipeRepository, RecipeMock.mockRecipe());
        RecipeStub.stubEmptyFindByIdAndCreatedByIgnoreCase(recipeRepository, RecipeMock.RECIPE_ID, UserHelperStub.TEST_EMAIL);

        var thrown = assertThrows(FavouriteRecipeException.class, () -> recipeQueryService.findById(1L),
                "Expected findById() to throw FavouriteRecipeException, but it didn't");

        assertEquals("recipeId", thrown.getExtraDetails());
        assertEquals(ErrorCode.RECIPE_NOT_FOUND.getMessageKey(), thrown.getErrorCode().getMessageKey());
    }

    @Test
    void testListing() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubListingRecipes(recipeRepository);

        final var recipeResponses = recipeQueryService.listRecipes(new RecipesSearchCriteria());

        assertEquals(10, recipeResponses.getData().size());
        verify(recipeRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testListingWithPassingAllSearchCriteria() {

        UserHelperStub.stubLoggedInUsernameValue(userHelper);
        RecipeStub.stubListingRecipes(recipeRepository);

        final var recipeResponses = recipeQueryService.listRecipes(buildRecipesSearchCriteria());

        assertEquals(100, recipeResponses.getTotalElements());
        verify(recipeRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    @NotNull
    private static RecipesSearchCriteria buildRecipesSearchCriteria() {

        final var recipesSearchCriteria = new RecipesSearchCriteria();
        recipesSearchCriteria.setInstructions("Test Instructions");
        recipesSearchCriteria.setIsVegetarian(Boolean.TRUE);
        recipesSearchCriteria.setServingCapacity(2);
        final var ingredientSearchCriteria = new IngredientSearchCriteria();
        ingredientSearchCriteria.setIsInclude(Boolean.TRUE);
        ingredientSearchCriteria.setNames(List.of(IngredientMock.INGREDIENT_NAME + 1));
        recipesSearchCriteria.setIngredients(ingredientSearchCriteria);
        return recipesSearchCriteria;
    }

}