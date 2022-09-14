package com.assessment.favouriterecipes.repository;

import com.assessment.favouriterecipes.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    Optional<Recipe> findByNameIgnoreCaseAndIdNot(String recipeName, Long recipeId);

    @Query("SELECT recipe FROM Recipe recipe LEFT JOIN FETCH recipe.ingredients " +
            "WHERE recipe.id = ?1 AND recipe.createdBy = ?2")
    Optional<Recipe> findByIdAndCreatedByIgnoreCase(Long recipeId, String loggedInUserEmail);

    Optional<Recipe> findByNameIgnoreCaseAndCreatedByIgnoreCase(String recipeName, String loggedInUserEmail);

}
