package com.assessment.favouriterecipes.specification;

import com.assessment.favouriterecipes.entity.Recipe;
import com.assessment.favouriterecipes.dto.request.IngredientSearchCriteria;
import com.assessment.favouriterecipes.dto.request.RecipesSearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.JoinType;
import java.util.List;
import java.util.Objects;

public final class RecipesSpecification {

    private RecipesSpecification() {

        throw new IllegalStateException("Utility class");
    }

    private static Specification<Recipe> withCreatedBy(String userEmail) {

        return (root, query, builder) ->
                StringUtils.isBlank(userEmail) ? builder.conjunction() : builder.equal(root.get("createdBy"), userEmail);
    }

    private static Specification<Recipe> withInstructions(String instructions) {

        return (root, query, builder) ->
                StringUtils.isBlank(instructions) ? builder.conjunction() : builder.like(builder.lower(root.get("instructions")), "%" + instructions.trim().toLowerCase() + "%");
    }

    private static Specification<Recipe> withIsVegetarian(Boolean isVegetarian) {

        return (root, query, builder) -> {
            if (Objects.isNull(isVegetarian)) {
                return builder.conjunction();
            }
            return Boolean.TRUE.equals(isVegetarian) ? builder.isTrue(root.get("isVegetarian")) : builder.isFalse(root.get("isVegetarian"));
        };
    }

    private static Specification<Recipe> withServingCapacity(Integer servingCapacity) {

        return (root, query, builder) ->
                Objects.isNull(servingCapacity) ? builder.conjunction() : builder.equal(root.get("servingCapacity"), servingCapacity);
    }

    private static Specification<Recipe> withIngredients(List<String> names) {

        return (root, query, builder) ->
                CollectionUtils.isEmpty(names) ? builder.conjunction() : builder.in(root.join("ingredients", JoinType.INNER).get("ingredient").get("name")).value(names);
    }

    private static Specification<Recipe> withoutIngredients(List<String> names) {

        return (root, query, builder) ->
                CollectionUtils.isEmpty(names) ? builder.conjunction() : builder.not(builder.in(root.join("ingredients", JoinType.INNER).get("ingredient").get("name")).value(names));
    }

    public static Specification<Recipe> buildRecipeSpecification(String userEmail, final RecipesSearchCriteria recipesSearchCriteria) {

        Specification<Recipe> specification = Specification.where(withCreatedBy(userEmail));

        if (Objects.nonNull(recipesSearchCriteria.getIsVegetarian())) {
            specification = specification.and(withIsVegetarian(recipesSearchCriteria.getIsVegetarian()));
        }

        if (Objects.nonNull(recipesSearchCriteria.getServingCapacity())) {
            specification = specification.and(withServingCapacity(recipesSearchCriteria.getServingCapacity()));
        }

        if (Objects.nonNull(recipesSearchCriteria.getInstructions())) {
            specification = specification.and(withInstructions(recipesSearchCriteria.getInstructions()));
        }

        final IngredientSearchCriteria ingredientSearchCriteria = recipesSearchCriteria.getIngredients();
        if (Objects.nonNull(ingredientSearchCriteria) && Objects.nonNull(ingredientSearchCriteria.getIsInclude())) {
            if (Boolean.TRUE.equals(ingredientSearchCriteria.getIsInclude())) {
                specification = specification.and(withIngredients(recipesSearchCriteria.getIngredients().getNames()));
            } else {
                specification = specification.and(withoutIngredients(recipesSearchCriteria.getIngredients().getNames()));
            }
        }

        return specification;
    }

}
