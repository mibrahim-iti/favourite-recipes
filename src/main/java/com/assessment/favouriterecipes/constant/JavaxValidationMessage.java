package com.assessment.favouriterecipes.constant;

import lombok.Generated;

public final class JavaxValidationMessage {

    public static final String INGREDIENT_NAME_NOT_NULL = "Ingredient name must not be null.";

    public static final String INGREDIENT_NAME_NOT_EMPTY = "Ingredient name must not be empty.";

    public static final String INGREDIENT_LIST_NOT_NULL = "Ingredient list must not be null.";

    public static final String INGREDIENT_LIST_NOT_EMPTY = "Ingredient list must not be empty.";

    public static final String INGREDIENT_WEIGHT_UNIT_NOT_NULL = "Ingredient weight unit must not be null.";

    public static final String INGREDIENT_WEIGHT_QUANTITY_NOT_NULL = "Ingredient weight quantity must not be null.";

    // Recipes

    public static final String RECIPE_NAME_NOT_NULL = "Recipe name must not be null.";

    public static final String RECIPE_NAME_NOT_EMPTY = "Recipe name must not be empty.";


    @Generated
    private JavaxValidationMessage() {

        throw new IllegalStateException("Utility class");
    }

}
