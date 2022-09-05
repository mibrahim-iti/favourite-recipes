package com.assessment.favouriterecipes.rest;

import com.assessment.favouriterecipes.dto.request.RecipeCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeUpdateRequest;
import com.assessment.favouriterecipes.dto.request.RecipesSearchCriteria;
import com.assessment.favouriterecipes.dto.response.PaginationResponse;
import com.assessment.favouriterecipes.dto.response.RecipeResponse;
import com.assessment.favouriterecipes.mock.RecipeMock;
import com.assessment.favouriterecipes.service.RecipeCommandService;
import com.assessment.favouriterecipes.service.RecipeQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeQueryService recipeQueryService;

    @Mock
    private RecipeCommandService recipeCommandService;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidRecipeId_whenGetByIdRestIsCalled_thenRecipeResponseObjectReturned() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        var recipeResponse = new RecipeResponse();
        recipeResponse.setId(RecipeMock.RECIPE_ID);
        recipeResponse.setName(RecipeMock.RECIPE_NAME);

        when(recipeQueryService.findById(RecipeMock.RECIPE_ID)).thenReturn(recipeResponse);

        final var responseEntity = recipeController.getRecipeById(1);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(RecipeMock.RECIPE_ID, responseEntity.getBody().getId());
        assertEquals(RecipeMock.RECIPE_NAME, responseEntity.getBody().getName());

        verify(recipeQueryService).findById(RecipeMock.RECIPE_ID);
    }

    @Test
    void givenValidRecipeSearchCriteria_whenGetAllMatchedRecipesRestIsCalled_thenRecipesListReturned() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        var recipeResponse = new RecipeResponse();
        recipeResponse.setId(RecipeMock.RECIPE_ID);
        recipeResponse.setName(RecipeMock.RECIPE_NAME);

        final var paginationResponse = PaginationResponse.<RecipeResponse>builder()
                .data(List.of(RecipeMock.mockListRecipeResponses().toArray(new RecipeResponse[0])))
                .totalElements(10L).totalPages(15)
                .build();
        final var recipesSearchCriteria = new RecipesSearchCriteria();

        when(recipeQueryService.listRecipes(recipesSearchCriteria)).thenReturn(paginationResponse);

        final var responseEntity = recipeController.listRecipes(recipesSearchCriteria);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(15, responseEntity.getBody().getTotalPages());
        assertEquals(10, responseEntity.getBody().getData().size());
        assertEquals(10, responseEntity.getBody().getTotalElements());

        verify(recipeQueryService).listRecipes(recipesSearchCriteria);
    }

    @Test
    void givenValidRecipeCreateRequestObject_whenPostCreateRecipeRestIsCalled_thenRecipeIdReturnedForSuccess() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(recipeCommandService.create(any(RecipeCreateRequest.class))).thenReturn(RecipeMock.RECIPE_ID);

        final var responseEntity = recipeController.createRecipe(new RecipeCreateRequest());

        assertNotNull(responseEntity.getBody());
        assertEquals(RecipeMock.RECIPE_ID, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void givenValidRecipeUpdateRequestObject_whenPostUpdateRecipeRestIsCalled_thenSuccessResponseBack() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(recipeCommandService).update(eq(RecipeMock.RECIPE_ID), any(RecipeUpdateRequest.class));

        final var responseEntity = recipeController.updateRecipe(RecipeMock.RECIPE_ID, new RecipeUpdateRequest());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void givenValidRecipeId_whenDeleteRestIsCalled_thenRecipeDeletedSuccessfully() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(recipeCommandService).deleteById(RecipeMock.RECIPE_ID);

        final var responseEntity = recipeController.deleteRecipe(RecipeMock.RECIPE_ID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
