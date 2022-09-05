package com.assessment.favouriterecipes.rest;

import com.assessment.favouriterecipes.config.OpenApiConfig;
import com.assessment.favouriterecipes.constant.Constants;
import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.dto.request.IngredientUpdateRequest;
import com.assessment.favouriterecipes.dto.response.IngredientResponse;
import com.assessment.favouriterecipes.service.IngredientCommandService;
import com.assessment.favouriterecipes.service.IngredientQueryService;
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
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
@SecurityRequirement(name = OpenApiConfig.OAUTH_SCHEME_NAME)
@Tag(name = Constants.TAG_INGREDIENT_APIS, description = Constants.INGREDIENT_CONTROLLER_DESC)
public class IngredientController {

    private final IngredientQueryService ingredientQueryService;

    private final IngredientCommandService ingredientCommandService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.GET_INGREDIENT_SUMMARY, description = Constants.GET_INGREDIENT_DESC, tags = {Constants.TAG_INGREDIENT_APIS})
    public ResponseEntity<IngredientResponse> getIngredientById(@PathVariable final long id) {

        return ResponseEntity.ok(ingredientQueryService.findById(id));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.CREATE_INGREDIENT_SUMMARY, description = Constants.CREATE_INGREDIENT_DESC, tags = {Constants.TAG_INGREDIENT_APIS})
    public ResponseEntity<Long> createIngredient(@Valid @RequestBody final IngredientCreateRequest ingredientCreateRequest) {

        final Long ingredientId = ingredientCommandService.create(ingredientCreateRequest);

        return new ResponseEntity<>(ingredientId, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = Constants.UPDATE_INGREDIENT_SUMMARY, description = Constants.UPDATE_INGREDIENT_DESC, tags = {Constants.TAG_INGREDIENT_APIS})
    public ResponseEntity<Void> updateIngredient(@PathVariable final long id, @Valid @RequestBody final IngredientUpdateRequest ingredientUpdateRequest) {

        ingredientCommandService.update(id, ingredientUpdateRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = Constants.DELETE_INGREDIENT_SUMMARY, description = Constants.DELETE_INGREDIENT_DESC, tags = {Constants.TAG_INGREDIENT_APIS})
    public ResponseEntity<Void> deleteIngredient(@PathVariable final long id) {

        ingredientCommandService.deleteById(id);

        return ResponseEntity.ok(null);
    }

}
