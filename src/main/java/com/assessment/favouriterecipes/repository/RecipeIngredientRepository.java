package com.assessment.favouriterecipes.repository;

import com.assessment.favouriterecipes.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

}
