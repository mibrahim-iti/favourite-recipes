package com.assessment.favouriterecipes.mapper;

import com.assessment.favouriterecipes.dto.request.IngredientCreateRequest;
import com.assessment.favouriterecipes.dto.request.IngredientUpdateRequest;
import com.assessment.favouriterecipes.dto.response.IngredientResponse;
import com.assessment.favouriterecipes.entity.Ingredient;
import lombok.Generated;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import javax.validation.constraints.NotNull;


@Generated
@Mapper(componentModel = "spring")
public interface IngredientMapper extends EntityDtoMapper<Ingredient, IngredientResponse> {

    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    Ingredient toEntity(@NotNull final IngredientCreateRequest ingredientCreateRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget final Ingredient ingredient, @NotNull final IngredientUpdateRequest ingredientUpdateRequest);

}
