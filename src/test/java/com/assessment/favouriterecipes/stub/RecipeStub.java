package com.assessment.favouriterecipes.stub;

import com.assessment.favouriterecipes.entity.Recipe;
import com.assessment.favouriterecipes.mock.RecipeMock;
import com.assessment.favouriterecipes.repository.RecipeRepository;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

public class RecipeStub {


    public static void stubListingRecipes(RecipeRepository recipeRepository) {

        final var page = new PageImpl(RecipeMock.mockListRecipes(), Pageable.ofSize(10), 100);
        doReturn(page).when(recipeRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    public static void stubRecipeSave(RecipeRepository recipeRepository) {

        doReturn(RecipeMock.mockRecipe()).when(recipeRepository).save(any(Recipe.class));
    }

    public static void stubRecipeFindById(RecipeRepository recipeRepository, Recipe recipe) {

        doReturn(Optional.of(recipe)).when(recipeRepository).findById(recipe.getId());
    }

    public static void stubRecipeDelete(RecipeRepository recipeRepository, Recipe recipe) {

        doNothing().when(recipeRepository).delete(recipe);
    }

    public static void stubFindByNameIgnoreCaseAndIdNot(final RecipeRepository recipeRepository, final String recipeName, final Long recipeId) {

        doReturn(Optional.of(RecipeMock.mockRecipe())).when(recipeRepository).findByNameIgnoreCaseAndIdNot(recipeName, recipeId);
    }

    public static void stubEmptyFindByNameIgnoreCaseAndIdNot(RecipeRepository recipeRepository, String recipeName, Long recipeId) {

        doReturn(Optional.empty()).when(recipeRepository).findByNameIgnoreCaseAndIdNot(recipeName, recipeId);
    }

    public static void stubFindByIdAndCreatedByIgnoreCase(RecipeRepository recipeRepository, Long recipeId, String loggedInUserEmail) {

        doReturn(RecipeMock.mockOptionalRecipe()).when(recipeRepository).findByIdAndCreatedByIgnoreCase(recipeId, loggedInUserEmail);
    }

    public static void stubFindByNameIgnoreCaseAndCreatedByIgnoreCase(RecipeRepository recipeRepository, String recipeName, String loggedInUserEmail) {

        doReturn(RecipeMock.mockOptionalRecipe()).when(recipeRepository).findByNameIgnoreCaseAndCreatedByIgnoreCase(recipeName, loggedInUserEmail);
    }

    public static void stubEmptyFindByNameIgnoreCaseAndCreatedByIgnoreCase(RecipeRepository recipeRepository, String recipeName, String loggedInUserEmail) {

        doReturn(Optional.empty()).when(recipeRepository).findByNameIgnoreCaseAndCreatedByIgnoreCase(recipeName, loggedInUserEmail);
    }

    public static void stubEmptyFindByIdAndCreatedByIgnoreCase(RecipeRepository recipeRepository, Long recipeId, String loggedInUserEmail) {

        doReturn(Optional.empty()).when(recipeRepository).findByIdAndCreatedByIgnoreCase(recipeId, loggedInUserEmail);
    }


    private RecipeStub() {

        throw new IllegalStateException("Utility class");
    }

}
