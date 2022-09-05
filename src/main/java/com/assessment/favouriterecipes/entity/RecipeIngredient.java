package com.assessment.favouriterecipes.entity;

import com.assessment.favouriterecipes.enums.WeightUnit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "recipes_ingredients")
public class RecipeIngredient extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;


    @Column(name = "weight_unit")
    @Enumerated(EnumType.STRING)
    private WeightUnit weightUnit;

    @Column(name = "weight_quantity")
    private Integer weightQuantity;

}
