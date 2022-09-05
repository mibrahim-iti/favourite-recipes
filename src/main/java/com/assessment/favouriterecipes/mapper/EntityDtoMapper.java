package com.assessment.favouriterecipes.mapper;

interface EntityDtoMapper<E, D> {

    D toDto(final E entity);

}
