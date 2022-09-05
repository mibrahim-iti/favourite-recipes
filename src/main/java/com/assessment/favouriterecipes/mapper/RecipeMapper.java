package com.assessment.favouriterecipes.mapper;

import com.assessment.favouriterecipes.dto.request.RecipeCreateRequest;
import com.assessment.favouriterecipes.dto.request.RecipeUpdateRequest;
import com.assessment.favouriterecipes.dto.response.RecipeResponse;
import com.assessment.favouriterecipes.entity.Recipe;
import lombok.Generated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import javax.validation.constraints.NotNull;


@Generated
@Mapper(componentModel = "spring", uses = RecipeIngredientMapper.class)
public interface RecipeMapper extends EntityDtoMapper<Recipe, RecipeResponse> {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    @Mapping(target = "ingredients", source = "ingredients.")
    RecipeResponse toDto(@NotNull final Recipe recipe);

    @Mapping(target = "ingredients", ignore = true)
    Recipe toEntity(@NotNull final RecipeCreateRequest recipeCreateRequest);

    @Mapping(target = "ingredients", ignore = true)
    void updateEntity(@MappingTarget final Recipe recipe, @NotNull final RecipeUpdateRequest recipeUpdateRequest);

}
