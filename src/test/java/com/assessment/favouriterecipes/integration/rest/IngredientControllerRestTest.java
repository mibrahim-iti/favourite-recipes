package com.assessment.favouriterecipes.integration.rest;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.integration.common.IntegrationRestTest;
import com.assessment.favouriterecipes.mock.IngredientMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IngredientControllerRestTest extends IntegrationRestTest {

    @Test
    void testNotFoundRecipeFindById() throws Exception {

        mockMvc.perform(get("/api/v1/ingredients/{id}", 1L).with(jwtRequestPostProcessor).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().json(errorCodeToJson(ErrorCode.INGREDIENT_NOT_FOUND, "ingredientId")));
    }

    @Test
    void testCreateIngredientFailed() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ingredients").with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(new IngredientCreateRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(errorCodeToJson(ErrorCode.VARIABLE_MUST_NOT_NULL, "ingredientCreateRequest.name")));
    }

    @Test
    void testCreateIngredientSuccess() throws Exception {

        var ingredientCreateRequest = new IngredientCreateRequest();
        ingredientCreateRequest.setName(IngredientMock.INGREDIENT_NAME);

        var result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ingredients").with(jwtRequestPostProcessor)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(toJson(ingredientCreateRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        var responseStr = result.getResponse().getContentAsString();

        var ingredientId = objectMapper.readValue(responseStr, Long.class);
        assertThat(ingredientId).isNotNull();
    }

}
