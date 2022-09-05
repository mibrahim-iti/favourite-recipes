package com.assessment.favouriterecipes.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "is_vegetarian", nullable = false)
    private Boolean isVegetarian = Boolean.FALSE;

    @Column(name = "serving_capacity", nullable = false)
    private Integer servingCapacity;

    @Column(name = "instructions", nullable = false, length = 3000)
    private String instructions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<RecipeIngredient> ingredients;

}
