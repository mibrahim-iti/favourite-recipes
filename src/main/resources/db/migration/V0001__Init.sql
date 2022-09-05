CREATE TABLE ingredients
(
    id            BIGSERIAL PRIMARY KEY,
    version       INT          NOT NULL,
    name          varchar(255) NOT NULL UNIQUE,
    created_by    varchar      NOT NULL,
    created_date  timestamp    NOT NULL,
    modified_date timestamp    NOT NULL
);

CREATE TABLE recipes
(
    id               BIGSERIAL PRIMARY KEY,
    version          INT          NOT NULL,
    name             varchar(255) NOT NULL,
    created_by       varchar      NOT NULL,
    created_date     timestamp    NOT NULL,
    modified_date    timestamp    NOT NULL,
    is_vegetarian    boolean      NOT NULL,
    serving_capacity INT          NOT NULL,
    instructions     varchar(3000)
);

CREATE TABLE recipes_ingredients
(
    id              BIGSERIAL PRIMARY KEY,
    version         INT       NOT NULL,
    created_by      varchar   NOT NULL,
    created_date    timestamp NOT NULL,
    modified_date   timestamp NOT NULL,
    ingredient_id   BIGSERIAL NOT NULL
        CONSTRAINT fk_ingredients REFERENCES ingredients (id),
    recipe_id       BIGSERIAL NOT NULL
        CONSTRAINT fk_recipe REFERENCES recipes (id),
    weight_unit     varchar(255),
    weight_quantity INT
);