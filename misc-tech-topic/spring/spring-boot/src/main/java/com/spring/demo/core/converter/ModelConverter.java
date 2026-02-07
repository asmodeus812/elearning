package com.spring.demo.core.converter;

import com.spring.demo.core.entity.AbstractEntity;

public interface ModelConverter<E extends AbstractEntity, M> {

    E convertFrom(M model);

    M convertFrom(E entity);

    ModelConverter<E, M> updateEntity(E entity, M model);

    ModelConverter<E, M> updateModel(M model, E entity);
}
