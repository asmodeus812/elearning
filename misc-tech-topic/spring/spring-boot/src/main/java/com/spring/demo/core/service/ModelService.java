package com.spring.demo.core.service;

import com.spring.demo.core.converter.ModelConverter;
import com.spring.demo.core.entity.AbstractEntity;
import com.spring.demo.core.repository.EntityRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityNotFoundException;

public interface ModelService<E extends AbstractEntity, M> {

    EntityRepository<E> repository();

    ModelConverter<E, M> converter();

    Class<E> entityClass();

    Class<M> modelClass();

    default long count() {
        return repository().count();
    }

    default Optional<M> find(Long id) {
        requireValidId(id);
        return repository().findById(id).map(converter()::convertFrom);
    }

    default M get(Long id) {
        requireValidId(id);
        return repository().findById(id).map(converter()::convertFrom).orElseThrow(EntityNotFoundException::new);
    }

    default boolean exists(Long id) {
        requireValidId(id);
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

    default M create(M model) {
        requireModel(model);
        beforeCreateModel(model);

        E entity = converter().convertFrom(model);
        requireNoId(entity.getId());

        beforeCreateEntity(entity);
        validateForSave(entity);

        E saved = repository().save(entity);
        afterCreateEntity(saved);

        M out = converter().convertFrom(saved);
        afterCreateModel(out);

        return out;
    }

    // default M update(M model) {
    // return update(model.id(), model);
    // }

    default M update(Long id, M model) {
        requireValidId(id);
        requireModel(model);

        beforeUpdateModel(id, model);
        E entity = repository().findById(id).orElseThrow(EntityNotFoundException::new);

        beforeUpdateEntity(entity);
        converter().updateEntity(entity, model);
        validateForSave(entity);

        E saved = repository().save(entity);
        afterUpdateEntity(saved);

        M out = converter().convertFrom(saved);
        afterUpdateModel(out);

        return out;
    }

    default boolean delete(Long id) {
        requireValidId(id);
        beforeDelete(id);
        if (!repository().existsById(id)) {
            throw new EntityNotFoundException();
        }
        repository().deleteById(id);
        afterDelete(id);
        return true;
    }

    default boolean deleteAll() {
        beforeDelete(null);
        repository().deleteAllInBatch();
        afterDelete(null);
        return true;
    }

    default void validateForSave(E entity) { /* no-op */ }

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

    default void requireValidId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
    }

    default void requireNoId(Long id) {
        if (id != null) {
            throw new IllegalArgumentException("Id must be null");
        }
    }


    default void requireModel(M model) {
        if (model == null) {
            throw new IllegalArgumentException("Model must not be null");
        }
    }

    default void requireEntity(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Model must not be null");
        }
    }
}
