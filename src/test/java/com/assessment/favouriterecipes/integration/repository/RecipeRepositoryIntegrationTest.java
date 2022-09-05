package com.assessment.favouriterecipes.integration.repository;

import com.assessment.favouriterecipes.integration.common.IntegrationRepositoryAbstractTest;
import com.assessment.favouriterecipes.mock.RecipeMock;
import com.assessment.favouriterecipes.repository.RecipeRepository;
import com.assessment.favouriterecipes.stub.UserHelperStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RecipeRepositoryIntegrationTest extends IntegrationRepositoryAbstractTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void testFindRecipeByNameIgnoreCaseAndIdNotFailed() {

        final var recipeByWrongId = recipeRepository.findByNameIgnoreCaseAndIdNot(RecipeMock.RECIPE_NAME, 1L);

        assertFalse(recipeByWrongId.isPresent());

        final var recipeId = createAndSaveRecipe();
        final var recipeByValidId = recipeRepository.findByNameIgnoreCaseAndIdNot(RecipeMock.RECIPE_NAME, recipeId);

        assertFalse(recipeByValidId.isPresent());
    }

    @Test
    void testFindRecipeByNameIgnoreCaseAndIdNotSuccess() {

        final var recipeId = createAndSaveRecipe();

        final var recipeOptional = recipeRepository.findByNameIgnoreCaseAndIdNot(RecipeMock.RECIPE_NAME, Long.valueOf(0));

        assertTrue(recipeOptional.isPresent());

        final var recipe = recipeOptional.get();

        assertEquals(recipeId, recipe.getId());
    }

    @Test
    void testFindRecipeByNameIgnoreCaseAndCreatedByIgnoreCaseFailed() {

        createAndSaveRecipe();

        final var recipeOptional = recipeRepository.findByNameIgnoreCaseAndCreatedByIgnoreCase(RecipeMock.RECIPE_NAME, "");

        assertFalse(recipeOptional.isPresent());
    }

    @Test
    void testFindRecipeByNameIgnoreCaseAndCreatedByIgnoreCaseSuccess() {

        final var recipeId = createAndSaveRecipe();

        final var recipeOptional = recipeRepository.findByNameIgnoreCaseAndCreatedByIgnoreCase(RecipeMock.RECIPE_NAME, UserHelperStub.TEST_EMAIL);

        assertTrue(recipeOptional.isPresent());

        final var recipe = recipeOptional.get();

        assertEquals(recipeId, recipe.getId());
    }

    @Test
    void testFindRecipeByIdAndCreatedByIgnoreCaseFailed() {

        final var recipeId = createAndSaveRecipe();

        final var recipeWithWrongEmailOptional = recipeRepository.findByIdAndCreatedByIgnoreCase(recipeId, "");

        assertFalse(recipeWithWrongEmailOptional.isPresent());

        final var recipeWithWrongIdOptional = recipeRepository.findByIdAndCreatedByIgnoreCase(0L, UserHelperStub.TEST_EMAIL);

        assertFalse(recipeWithWrongIdOptional.isPresent());
    }

    @Test
    void testFindRecipeByIdAndCreatedByIgnoreCaseSuccess() {

        final var recipeId = createAndSaveRecipe();

        final var recipeOptional = recipeRepository.findByIdAndCreatedByIgnoreCase(recipeId, UserHelperStub.TEST_EMAIL);

        assertTrue(recipeOptional.isPresent());

        final var recipe = recipeOptional.get();

        assertEquals(recipeId, recipe.getId());
    }

    @NotNull
    private Long createAndSaveRecipe() {

        var recipe = RecipeMock.mockRecipeWithoutIdAndWithoutIngredients();
        recipe.setCreatedBy(UserHelperStub.TEST_EMAIL);

        var savedRecipe = recipeRepository.save(recipe);

        assertThat(savedRecipe.getId()).isNotNull();
        assertThat(savedRecipe.getVersion()).isNotNull();
        assertThat(savedRecipe.getCreatedBy()).isNotNull();
        assertThat(savedRecipe.getCreatedDate()).isNotNull();
        assertThat(savedRecipe.getModifiedDate()).isNotNull();

        assertThat(savedRecipe.getCreatedBy()).isEqualTo(UserHelperStub.TEST_EMAIL);

        return recipe.getId();
    }

}
