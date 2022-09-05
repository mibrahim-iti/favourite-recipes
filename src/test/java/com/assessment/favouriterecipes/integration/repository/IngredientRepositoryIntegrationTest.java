package com.assessment.favouriterecipes.integration.repository;

import com.assessment.favouriterecipes.integration.common.IntegrationRepositoryAbstractTest;
import com.assessment.favouriterecipes.mock.IngredientMock;
import com.assessment.favouriterecipes.repository.IngredientRepository;
import com.assessment.favouriterecipes.stub.UserHelperStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IngredientRepositoryIntegrationTest extends IntegrationRepositoryAbstractTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void testFindIngredientByNameIgnoreCaseInvalid() {

        final var ingredientOptional = ingredientRepository.findByNameIgnoreCase(IngredientMock.INGREDIENT_NAME);

        assertFalse(ingredientOptional.isPresent());
    }

    @Test
    void testFindIngredientByNameIgnoreCaseSuccess() {

        createAndSaveIngredient();

        final var ingredientOptional = ingredientRepository.findByNameIgnoreCase(IngredientMock.INGREDIENT_NAME);

        assertTrue(ingredientOptional.isPresent());
    }

    private void createAndSaveIngredient() {

        var ingredient = IngredientMock.mockIngredientWithoutId();
        ingredient.setCreatedBy(UserHelperStub.TEST_EMAIL);

        var savedIngredient = ingredientRepository.save(ingredient);

        assertThat(savedIngredient.getId()).isNotNull();
        assertThat(ingredient.getVersion()).isNotNull();
        assertThat(savedIngredient.getCreatedBy()).isNotNull();
        assertThat(savedIngredient.getCreatedDate()).isNotNull();
        assertThat(savedIngredient.getModifiedDate()).isNotNull();

        assertThat(savedIngredient.getCreatedBy()).isEqualTo(UserHelperStub.TEST_EMAIL);
    }

}
