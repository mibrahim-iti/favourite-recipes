package com.assessment.favouriterecipes.integration.rest;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.dto.request.RecipeCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeUpdateRequest;
import com.assessment.favouriterecipes.integration.common.IntegrationRestTest;
import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.mock.RecipeIngredientMock;
import com.assessment.favouriterecipes.mock.RecipeMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerRestIntegrationTest extends IntegrationRestTest {

    @Test
    void testNotFoundRecipeFindById() throws Exception {

        mockMvc.perform(get("/api/v1/recipes/{id}", 1L).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is(ErrorCode.RECIPE_NOT_FOUND.getCode())))
                .andExpect(jsonPath("$.messageKey", equalTo(ErrorCode.RECIPE_NOT_FOUND.getMessageKey())))
                .andExpect(jsonPath("$.value", equalTo("recipeId")));
    }

    @Test
    void testRecipeFindByIdSuccess() throws Exception {

        Long recipeId = createValidRecipeAndValidateResponse();

        mockMvc.perform(get("/api/v1/recipes/{id}", recipeId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(recipeId.intValue())))
                .andExpect(jsonPath("$.name", is(RecipeMock.RECIPE_NAME)))
                .andExpect(jsonPath("$.ingredients.size()", is(1)))
                .andExpect(jsonPath("$.ingredients[0].name", is(IngredientMock.INGREDIENT_NAME)));
    }

    @Test
    void testCreateRecipeFailed() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recipes").with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(new RecipeCreateRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ErrorCode.VARIABLE_MUST_NOT_NULL.getCode())))
                .andExpect(jsonPath("$.messageKey", equalTo(ErrorCode.VARIABLE_MUST_NOT_NULL.getMessageKey())))
                .andExpect(jsonPath("$.value", equalTo("recipeCreateRequest.name")));
    }

    @Test
    void testCreateRecipeWithNameFailed() throws Exception {

        var recipeCreateRequest = new RecipeCreateRequest();
        recipeCreateRequest.setName(RecipeMock.RECIPE_NAME);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recipes").with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(recipeCreateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(ErrorCode.COLLECTION_MUST_NOT_EMPTY.getCode())))
                .andExpect(jsonPath("$.messageKey", equalTo(ErrorCode.COLLECTION_MUST_NOT_EMPTY.getMessageKey())))
                .andExpect(jsonPath("$.value", equalTo("recipeCreateRequest.ingredients")));
    }

    @Test
    void testCreateRecipeSuccess() throws Exception {

        Long recipeId = createValidRecipeAndValidateResponse();
        assertThat(recipeId).isNotNull();
    }

    @Test
    void testUpdateRecipeSuccess() throws Exception {

        Long recipeId = createValidRecipeAndValidateResponse();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/recipes/{id}", recipeId).with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(createValidUpdateRequest())))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(get("/api/v1/recipes/{id}", recipeId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(recipeId.intValue())))
                .andExpect(jsonPath("$.name", is(RecipeMock.UPDATED_RECIPE_NAME)))
                .andExpect(jsonPath("$.ingredients.size()", is(1)))
                .andExpect(jsonPath("$.ingredients[0].name", is(IngredientMock.UPDATED_INGREDIENT_NAME)));
    }

    private Long createValidRecipeAndValidateResponse() throws Exception {

        var recipeCreateRequest = new RecipeCreateRequest();
        recipeCreateRequest.setName(RecipeMock.RECIPE_NAME);
        recipeCreateRequest.setInstructions(RecipeMock.RECIPE_INSTRUCTIONS);

        recipeCreateRequest.setIngredients(List.of(RecipeIngredientMock.mockRecipeIngredientCreateRequest()));

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/recipes").with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(recipeCreateRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var responseStr = result.getResponse().getContentAsString();

        return objectMapper.readValue(responseStr, Long.class);
    }

    private RecipeUpdateRequest createValidUpdateRequest() {

        var recipeUpdateRequest = new RecipeUpdateRequest();
        recipeUpdateRequest.setName(RecipeMock.UPDATED_RECIPE_NAME);
        recipeUpdateRequest.setInstructions(RecipeMock.UPDATED_RECIPE_INSTRUCTIONS);

        recipeUpdateRequest.setIngredients(List.of(RecipeIngredientMock.mockRecipeIngredientUpdateRequest()));

        return recipeUpdateRequest;
    }


    @Test
    void testDeleteRecipeFailed() throws Exception {

        mockMvc.perform(delete("/api/v1/recipes/{id}", 1L).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().json(errorCodeToJson(ErrorCode.RECIPE_NOT_FOUND, "recipeId")));
    }

    @Test
    void testDeleteRecipeSuccess() throws Exception {

        Long recipeId = createValidRecipeAndValidateResponse();

        mockMvc.perform(delete("/api/v1/recipes/{id}", recipeId).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}