package com.assessment.favouriterecipes.rest;

import com.assessment.favouriterecipes.config.OpenApiConfig;
import com.assessment.favouriterecipes.constant.Constants;
import com.assessment.favouriterecipes.dto.request.RecipeCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeUpdateRequest;
import com.assessment.favouriterecipes.dto.request.RecipesSearchCriteria;
import com.assessment.favouriterecipes.dto.response.PaginationResponse;
import com.assessment.favouriterecipes.dto.response.RecipeResponse;
import com.assessment.favouriterecipes.service.RecipeCommandService;
import com.assessment.favouriterecipes.service.RecipeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@SecurityRequirement(name = OpenApiConfig.OAUTH_SCHEME_NAME)
@Tag(name = Constants.TAG_RECIPE_APIS, description = Constants.RECIPE_CONTROLLER_DESC)
public class RecipeController {

    private final RecipeQueryService recipeQueryService;

    private final RecipeCommandService recipeCommandService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.GET_RECIPE_SUMMARY, description = Constants.GET_RECIPE_DESC, tags = {Constants.TAG_RECIPE_APIS})
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable final long id) {

        return ResponseEntity.ok(recipeQueryService.findById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.CREATE_RECIPE_SUMMARY, description = Constants.CREATE_RECIPE_DESC, tags = {Constants.TAG_RECIPE_APIS})
    public ResponseEntity<Long> createRecipe(@Valid @RequestBody final RecipeCreateRequest recipeCreateRequest) {

        final Long recipeId = recipeCommandService.create(recipeCreateRequest);

        return new ResponseEntity<>(recipeId, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.UPDATE_RECIPE_SUMMARY, description = Constants.UPDATE_RECIPE_DESC, tags = {Constants.TAG_RECIPE_APIS})
    public ResponseEntity<Void> updateRecipe(@PathVariable final long id, @Valid @RequestBody final RecipeUpdateRequest recipeUpdateRequest) {

        recipeCommandService.update(id, recipeUpdateRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = Constants.DELETE_RECIPE_SUMMARY, description = Constants.DELETE_RECIPE_DESC, tags = {Constants.TAG_RECIPE_APIS})
    public ResponseEntity<Void> deleteRecipe(@PathVariable final long id) {

        recipeCommandService.deleteById(id);

        return ResponseEntity.ok(null);
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.LIST_RECIPE_SUMMARY, description = Constants.LIST_RECIPE_DESC, tags = {Constants.TAG_RECIPE_APIS})
    public ResponseEntity<PaginationResponse<RecipeResponse>> listRecipes(@Valid @RequestBody final RecipesSearchCriteria recipesSearchCriteria) {

        final PaginationResponse<RecipeResponse> recipes = recipeQueryService.listRecipes(recipesSearchCriteria);

        return ResponseEntity.ok(recipes);
    }

}
