package com.assessment.favouriterecipes.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

}
