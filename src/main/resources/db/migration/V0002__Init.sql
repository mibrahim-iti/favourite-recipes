CREATE INDEX INGREDIENTS_NAME_IDX on INGREDIENTS ("name");
CREATE INDEX INGREDIENTS_NAME_CREATED_BY on INGREDIENTS ("created_by");
CREATE INDEX INGREDIENTS_NAME_CREATED_DATE on INGREDIENTS ("created_date");

CREATE INDEX RECIPES_NAME_IDX on RECIPES ("name");
CREATE INDEX RECIPES_NAME_CREATED_BY on RECIPES ("created_by");
CREATE INDEX RECIPES_NAME_CREATED_DATE on RECIPES ("created_date");
CREATE INDEX RECIPES_IS_VEGETARIAN_IDX on RECIPES ("is_vegetarian");

CREATE INDEX RECIPES_INGREDIENTS_RECIPE_ID_IDX on RECIPES_INGREDIENTS ("recipe_id");
CREATE INDEX RECIPES_INGREDIENTS_INGREDIENT_ID_IDX on RECIPES_INGREDIENTS ("ingredient_id");

