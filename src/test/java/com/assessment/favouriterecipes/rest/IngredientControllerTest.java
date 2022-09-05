package com.assessment.favouriterecipes.rest;

import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.dto.request.IngredientUpdateRequest;
import com.assessment.favouriterecipes.dto.response.IngredientResponse;
import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.service.IngredientCommandService;
import com.assessment.favouriterecipes.service.IngredientQueryService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
class IngredientControllerTest {

    @InjectMocks
    private IngredientController ingredientController;

    @Mock
    private IngredientQueryService ingredientQueryService;

    @Mock
    private IngredientCommandService ingredientCommandService;

    @BeforeEach
    public void init() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidIngredientId_whenGetRestIsCalled_thenIngredientResponseObjectReturned() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        var ingredient = new IngredientResponse();
        ingredient.setId(IngredientMock.INGREDIENT_ID);
        ingredient.setName(IngredientMock.INGREDIENT_NAME);

        when(ingredientQueryService.findById(IngredientMock.INGREDIENT_ID)).thenReturn(ingredient);

        final var responseEntity = ingredientController.getIngredientById(1);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(IngredientMock.INGREDIENT_ID, responseEntity.getBody().getId());
    }

    @Test
    void givenValidIngredientCreateRequestObject_whenPostCreateIngredientRestIsCalled_thenIngredientIdReturnedForSuccess() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(ingredientCommandService.create(any(IngredientCreateRequest.class))).thenReturn(IngredientMock.INGREDIENT_ID);

        final var responseEntity = ingredientController.createIngredient(new IngredientCreateRequest());

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(IngredientMock.INGREDIENT_ID, responseEntity.getBody());
    }

    @Test
    void givenValidIngredientUpdateRequestObject_whenPostUpdateIngredientRestIsCalled_thenSuccessfullyResponseBack() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(ingredientCommandService).update(eq(IngredientMock.INGREDIENT_ID), any(IngredientUpdateRequest.class));

        final var responseEntity = ingredientController.updateIngredient(IngredientMock.INGREDIENT_ID, new IngredientUpdateRequest());

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void givenValidIngredientId_whenDeleteRestIsCalled_thenIngredientDeletedSuccessfully() {

        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        doNothing().when(ingredientCommandService).deleteById(IngredientMock.INGREDIENT_ID);

        final var responseEntity = ingredientController.deleteIngredient(IngredientMock.INGREDIENT_ID);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
