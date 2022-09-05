package com.assessment.favouriterecipes.mapper;

import com.assessment.favouriterecipes.entity.RecipeIngredient;
import com.assessment.favouriterecipes.dto.request.RecipeIngredientCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeIngredientUpdateRequest;
import com.assessment.favouriterecipes.dto.response.RecipeIngredientResponse;
import lombok.Generated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.validation.constraints.NotNull;


@Generated
@Mapper(componentModel = "spring", uses = IngredientMapper.class)
public interface RecipeIngredientMapper {

    RecipeIngredientMapper INSTANCE = Mappers.getMapper(RecipeIngredientMapper.class);

    @Mapping(target = "name", source = "ingredient.name")
    RecipeIngredientResponse toDto(@NotNull final RecipeIngredient recipeIngredient);
    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    RecipeIngredient toEntity(@NotNull final RecipeIngredientCreateRequest recipeIngredientCreateRequest);
    RecipeIngredient toEntity(@NotNull final RecipeIngredientUpdateRequest recipeIngredientUpdateRequest);

}
