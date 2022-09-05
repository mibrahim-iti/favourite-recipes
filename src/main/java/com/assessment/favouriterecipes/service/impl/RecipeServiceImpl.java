package com.assessment.favouriterecipes.service.impl;

import com.assessment.favouriterecipes.dto.error.ErrorCode;
import com.assessment.favouriterecipes.dto.request.*;
import com.assessment.favouriterecipes.dto.response.PaginationResponse;
import com.assessment.favouriterecipes.dto.response.RecipeResponse;
import com.assessment.favouriterecipes.entity.Ingredient;
import com.assessment.favouriterecipes.entity.Recipe;
import com.assessment.favouriterecipes.entity.RecipeIngredient;
import com.assessment.favouriterecipes.exception.FavouriteRecipeException;
import com.assessment.favouriterecipes.mapper.RecipeIngredientMapper;
import com.assessment.favouriterecipes.mapper.RecipeMapper;
import com.assessment.favouriterecipes.repository.IngredientRepository;
import com.assessment.favouriterecipes.repository.RecipeIngredientRepository;
import com.assessment.favouriterecipes.repository.RecipeRepository;
import com.assessment.favouriterecipes.service.RecipeCommandService;
import com.assessment.favouriterecipes.service.RecipeQueryService;
import com.assessment.favouriterecipes.specification.PageableCriteria;
import com.assessment.favouriterecipes.specification.RecipesSpecification;
import com.assessment.favouriterecipes.util.UserHelper;
import com.assessment.favouriterecipes.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeCommandService, RecipeQueryService {

    public static final String RECIPE_ID = "recipeId";

    public static final String RECIPE_NAME = "recipeName";

    private final UserHelper userHelper;

    private final RecipeMapper recipeMapper;

    private final RecipeIngredientMapper recipeIngredientMapper;

    private final RecipeRepository recipeRepository;

    private final IngredientRepository ingredientRepository;

    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    @Transactional
    public RecipeResponse findById(final Long recipeId) {

        Validator.validateNotNull(recipeId, RECIPE_ID);

        final var recipe = findRecipeByIdAndLoggedInUsername(recipeId);

        return recipeMapper.toDto(recipe);
    }

    @Override
    public void deleteById(final Long recipeId) {

        Validator.validateNotNull(recipeId, RECIPE_ID);

        final var recipe = findRecipeByIdAndLoggedInUsername(recipeId);

        recipeRepository.delete(recipe);
    }

    @Override
    @Transactional
    public Long create(final RecipeCreateRequest recipeCreateRequest) {

        Validator.validateNotNull(recipeCreateRequest, "recipeCreateRequest");
        Validator.validateNotBlank(recipeCreateRequest.getName(), "recipeCreateRequest.name");
        Validator.validateNotEmpty(recipeCreateRequest.getIngredients(), "recipeCreateRequest.ingredients");
        validateRecipeNameIsNotExistInDB(recipeCreateRequest.getName());

        final var recipe = recipeMapper.toEntity(recipeCreateRequest);

        final var savedRecipe = recipeRepository.save(recipe);

        final var recipeIngredients = prepareIngredients(savedRecipe, recipeCreateRequest.getIngredients());
        recipeIngredientRepository.saveAll(recipeIngredients);

        return savedRecipe.getId();
    }

    @Override
    @Transactional
    public void update(final Long recipeId, final RecipeUpdateRequest recipeUpdateRequest) {

        Validator.validateNotNull(recipeId, RECIPE_ID);
        Validator.validateNotNull(recipeUpdateRequest, "recipeUpdateRequest");
        Validator.validateNotBlank(recipeUpdateRequest.getName(), "recipeUpdateRequest.name");
        Validator.validateNotEmpty(recipeUpdateRequest.getIngredients(), "recipeUpdateRequest.ingredients");

        final var recipe = findRecipeByIdAndLoggedInUsername(recipeId);
        validateRecipeName(recipeId, recipeUpdateRequest.getName());

        recipeMapper.updateEntity(recipe, recipeUpdateRequest);

        final var recipeIngredients = prepareIngredientsForUpdate(recipe, recipeUpdateRequest.getIngredients());
        recipeIngredientRepository.saveAll(recipeIngredients);

        recipeIngredientRepository.deleteAll(recipe.getIngredients());
        recipe.setIngredients(recipeIngredients);

    }

    @Override
    @Transactional
    public PaginationResponse<RecipeResponse> listRecipes(final RecipesSearchCriteria recipesSearchCriteria) {

        Validator.validateNotNull(recipesSearchCriteria, "recipesSearchCriteria");

        final PageableCriteria pageableCriteria = PageableCriteria.builder()
                .page(recipesSearchCriteria.getPage())
                .size(recipesSearchCriteria.getSize())
                .direction(recipesSearchCriteria.getDirection()).build();
        final var pageRequest = getPageRequest(pageableCriteria);

        final var recipeSpecification = RecipesSpecification.buildRecipeSpecification(userHelper.getLoggedInUsernameValue(), recipesSearchCriteria);
        final var page = recipeRepository.findAll(recipeSpecification, pageRequest);

        page.getTotalElements();
        page.getTotalPages();
        page.getContent();

        final var recipeResponses = page.getContent().stream().map(recipeMapper::toDto).collect(Collectors.toList());


        return PaginationResponse.<RecipeResponse>builder()
                .totalPages(page.getTotalPages()).totalElements(page.getTotalElements()).data(recipeResponses)
                .build();
    }

    private void validateRecipeNameIsNotExistInDB(String recipeName) {

        recipeRepository.findByNameIgnoreCaseAndCreatedByIgnoreCase(recipeName, userHelper.getLoggedInUsernameValue())
                .ifPresent(recipe -> {
                    throw new FavouriteRecipeException(ErrorCode.RECIPE_DUPLICATION_FOUND, RECIPE_NAME);
                });
    }

    private PageRequest getPageRequest(final PageableCriteria pageableCriteria) {

        return PageRequest.of(pageableCriteria.getPage(), pageableCriteria.getSize(), pageableCriteria.getDirection(), "createdDate");
    }

    private Recipe findRecipeByIdAndLoggedInUsername(final Long recipeId) {

        return recipeRepository.findByIdAndCreatedByIgnoreCase(recipeId, userHelper.getLoggedInUsernameValue())
                .orElseThrow(() -> new FavouriteRecipeException(ErrorCode.RECIPE_NOT_FOUND, RECIPE_ID));
    }

    private void validateRecipeName(final Long recipeId, final String recipeName) {

        recipeRepository.findByNameIgnoreCaseAndIdNot(recipeName, recipeId)
                .ifPresent(ingredient -> {
                    throw new FavouriteRecipeException(ErrorCode.RECIPE_DUPLICATION_FOUND, RECIPE_NAME);
                });
    }

    private List<RecipeIngredient> prepareIngredients(final Recipe recipe, final List<RecipeIngredientCreateRequest> ingredients) {

        return ingredients.stream().map(ingredient -> mapToRecipeIngredient(recipe, ingredient)).collect(Collectors.toList());

    }

    private RecipeIngredient mapToRecipeIngredient(final Recipe recipe, final RecipeIngredientCreateRequest recipeIngredientCreateRequest) {

        var recipeIngredient = recipeIngredientMapper.toEntity(recipeIngredientCreateRequest);

        recipeIngredient.setRecipe(recipe);
        final var ingredient = getOrSaveAndGetIngredient(recipeIngredientCreateRequest);
        recipeIngredient.setIngredient(ingredient);

        return recipeIngredient;
    }

    private Ingredient getOrSaveAndGetIngredient(final RecipeIngredientCreateRequest recipeIngredientCreateRequest) {

        return ingredientRepository.findByNameIgnoreCase(recipeIngredientCreateRequest.getName())
                .orElseGet(() -> createNewIngredient(recipeIngredientCreateRequest.getName()));
    }

    private Ingredient createNewIngredient(final String ingredientName) {

        var newIngredient = new Ingredient();

        newIngredient.setName(ingredientName);

        return ingredientRepository.save(newIngredient);
    }

    private List<RecipeIngredient> prepareIngredientsForUpdate(Recipe recipe, List<RecipeIngredientUpdateRequest> ingredients) {

        return ingredients.stream().map(ingredient -> mapToRecipeIngredient(recipe, ingredient)).collect(Collectors.toList());
    }

    private RecipeIngredient mapToRecipeIngredient(final Recipe recipe, final RecipeIngredientUpdateRequest recipeIngredientUpdateRequest) {

        var recipeIngredient = recipeIngredientMapper.toEntity(recipeIngredientUpdateRequest);

        recipeIngredient.setRecipe(recipe);

        final var ingredient = getOrSaveAndGetIngredient(recipeIngredientUpdateRequest);

        recipeIngredient.setIngredient(ingredient);

        return recipeIngredient;
    }

    private Ingredient getOrSaveAndGetIngredient(final RecipeIngredientUpdateRequest recipeIngredientUpdateRequest) {

        return ingredientRepository.findByNameIgnoreCase(recipeIngredientUpdateRequest.getName())
                .orElseGet(() -> createNewIngredient(recipeIngredientUpdateRequest.getName()));
    }


}
