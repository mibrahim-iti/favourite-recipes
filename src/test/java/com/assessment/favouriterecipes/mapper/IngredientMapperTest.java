package com.assessment.favouriterecipes.mapper;

import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.dto.response.IngredientResponse;
import com.assessment.favouriterecipes.entity.Ingredient;
import com.assessment.favouriterecipes.mock.IngredientMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

@ActiveProfiles("test")
class IngredientMapperTest {

    @Test
    void testNull() {

        final var ingredientMapper = Mappers.getMapper(IngredientMapper.class);

        assertThat(ingredientMapper.toDto(null)).isNull();
        assertThat(ingredientMapper.toEntity(null)).isNull();

        final var ingredient = new Ingredient();
        ingredientMapper.updateEntity(ingredient, null);
        Assertions.assertAll(
                () -> assertThat(ingredient.getId()).isNull(),
                () -> assertThat(ingredient.getName()).isNull()
        );
    }

    @Test
    void testNotNull() {

        final var ingredientMapper = Mappers.getMapper(IngredientMapper.class);

        assertThat(ingredientMapper.toDto(new Ingredient())).isNotNull().asInstanceOf(type(IngredientResponse.class));
        assertThat(ingredientMapper.toEntity(new IngredientCreateRequest())).isNotNull().asInstanceOf(type(Ingredient.class));

        final var ingredient = IngredientMock.mockIngredient();

        var ingredientUpdateRequest = IngredientMock.mockIngredientUpdateRequest();
        final var updatedName = "Updated";
        ingredientUpdateRequest.setName(updatedName);

        ingredientMapper.updateEntity(ingredient, ingredientUpdateRequest);

        assertThat(ingredient.getId()).isNotNull().isEqualTo(1L);
        assertThat(ingredient.getName()).isNotNull().isEqualTo(updatedName);
    }

}