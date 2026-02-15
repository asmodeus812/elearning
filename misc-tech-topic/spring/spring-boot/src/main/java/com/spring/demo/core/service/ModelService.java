package com.spring.demo.core.service;

import com.spring.demo.core.converter.ModelConverter;
import com.spring.demo.core.entity.AbstractEntity;
import com.spring.demo.core.repository.EntityRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public interface ModelService<E extends AbstractEntity, M> {

    EntityRepository<E> repository();

    ModelConverter<E, M> converter();

    Class<E> entityClass();

    Class<M> modelClass();

    default long count() {
        return repository().count();
    }

    default Optional<M> find(Long id) {
        requirePresentId(id);
        return repository().findById(id).map(converter()::convertFrom);
    }

    default M get(Long id) {
        requirePresentId(id);
        return repository().findById(id).map(converter()::convertFrom).orElseThrow(EntityNotFoundException::new);
    }

    default boolean exists(Long id) {
        requirePresentId(id);
        return repository().existsById(id);
    }

    default List<M> findAll() {
        return repository().findAll().stream().map(converter()::convertFrom).toList();
    }

    default Page<M> findAll(Pageable pageable) {
        return repository().findAll(pageable).map(converter()::convertFrom);
    }

    default List<M> getAll() {
        return findAll();
    }

    default Page<M> getAll(Pageable pageable) {
        return findAll(pageable);
    }

    @Transactional
    default M create(M model) {
        requireModel(model);
        beforeCreateModel(model);

        E entity = converter().convertFrom(model);
        requireEmptyId(entity.getId());

        beforeCreateEntity(entity);
        validateCreateEntity(entity);

        E saved = repository().save(entity);
        afterCreateEntity(saved);

        M out = converter().convertFrom(saved);
        afterCreateModel(out);

        return out;
    }

    @Transactional
    default M update(Long id, M model) {
        requirePresentId(id);
        requireModel(model);

        beforeUpdateModel(id, model);
        E entity = repository().findById(id).orElseThrow(EntityNotFoundException::new);

        beforeUpdateEntity(entity);
        converter().updateEntity(entity, model);
        validateCreateEntity(entity);

        E saved = repository().save(entity);
        afterUpdateEntity(saved);

        M out = converter().convertFrom(saved);
        afterUpdateModel(out);

        return out;
    }

    @Transactional
    default boolean delete(Long id) {
        requirePresentId(id);
        beforeDelete(id);
        if (!repository().existsById(id)) {
            return false;
        }
        repository().deleteById(id);
        afterDelete(id);
        return true;
    }

    @Transactional
    default boolean deleteAll() {
        beforeDelete(null);
        repository().deleteAllInBatch();
        afterDelete(null);
        return true;
    }

    default void validateCreateEntity(E entity) { /* no-op */ }

    default void validateCreateModel(M model) { /* no-op */ }

    default void beforeCreateModel(M model) { /* no-op */ }

    default void afterCreateModel(M created) { /* no-op */ }

    default void beforeUpdateModel(Long id, M model) { /* no-op */ }

    default void afterUpdateModel(M updated) { /* no-op */ }

    default void beforeCreateEntity(E entity) { /* no-op */ }

    default void afterCreateEntity(E saved) { /* no-op */ }

    default void beforeUpdateEntity(E entity) { /* no-op */ }

    default void afterUpdateEntity(E saved) { /* no-op */ }

    default void beforeDelete(Long id) { /* no-op */ }

    default void afterDelete(Long id) { /* no-op */ }

    default void requirePresentId(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Id must not be null");
        }
    }

    default void requireEmptyId(Long id) {
        if (!Objects.isNull(id)) {
            throw new IllegalArgumentException("Id must be null");
        }
    }


    default void requireModel(M model) {
        if (Objects.isNull(model)) {
            throw new IllegalArgumentException("Model must not be null");
        }
    }

    default void requireEntity(E entity) {
        if (Objects.isNull(entity)) {
            throw new IllegalArgumentException("Entity must not be null");
        }
    }
}
