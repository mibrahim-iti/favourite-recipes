package com.assessment.favouriterecipes.service.impl;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.dto.request.IngredientUpdateRequest;
import com.assessment.favouriterecipes.dto.response.IngredientResponse;
import com.assessment.favouriterecipes.entity.Ingredient;
import com.assessment.favouriterecipes.exception.FavouriteRecipeException;
import com.assessment.favouriterecipes.mapper.IngredientMapper;
import com.assessment.favouriterecipes.repository.IngredientRepository;
import com.assessment.favouriterecipes.service.IngredientCommandService;
import com.assessment.favouriterecipes.service.IngredientQueryService;
import com.assessment.favouriterecipes.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientQueryService, IngredientCommandService {

    public static final String INGREDIENT_ID = "ingredientId";

    private final IngredientMapper ingredientMapper;

    private final IngredientRepository ingredientRepository;

    @Override
    public IngredientResponse findById(final Long ingredientId) {

        Validator.validateNotNull(ingredientId, INGREDIENT_ID);

        final var ingredient = findIngredientById(ingredientId);

        return ingredientMapper.toDto(ingredient);
    }

    @Override
    public void deleteById(final Long ingredientId) {

        Validator.validateNotNull(ingredientId, INGREDIENT_ID);

        ingredientRepository.deleteById(ingredientId);
    }

    @Override
    public Long create(final IngredientCreateRequest ingredientCreateRequest) {

        Validator.validateNotNull(ingredientCreateRequest, "ingredientCreateRequest");
        Validator.validateNotBlank(ingredientCreateRequest.getName(), "ingredientCreateRequest.name");

        final var ingredient = ingredientMapper.toEntity(ingredientCreateRequest);

        return ingredientRepository.save(ingredient).getId();
    }

    @Override
    public void update(final Long ingredientId, final IngredientUpdateRequest ingredientUpdateRequest) {

        Validator.validateNotNull(ingredientId, INGREDIENT_ID);
        Validator.validateNotNull(ingredientUpdateRequest, "ingredientUpdateRequest");
        Validator.validateNotNull(ingredientUpdateRequest.getName(), "ingredientUpdateRequest.name");

        final var ingredient = findIngredientById(ingredientId);

        ingredientMapper.updateEntity(ingredient, ingredientUpdateRequest);

        ingredientRepository.save(ingredient);
    }

    private Ingredient findIngredientById(final Long ingredientId) {

        Validator.validateNotNull(ingredientId, INGREDIENT_ID);

        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new FavouriteRecipeException(ErrorCode.INGREDIENT_NOT_FOUND, INGREDIENT_ID));
    }

}
