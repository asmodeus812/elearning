package com.spring.demo.core.api;

import java.util.Optional;

public interface CriteriaCollection {

    <T> T getRaw(String name) throws ClassCastException;

    boolean hasCriteria(String name) throws ClassCastException;

    CriteriaCollection addCriteria(String name, Object value) throws NullPointerException;

    CriteriaCollection removeCriteria(String name) throws NullPointerException;

    default <T> Optional<T> get(String name) throws ClassCastException {
        return Optional.ofNullable(getRaw(name));
    }

    default <T> Optional<T> get(String name, CriteriaCondition<T> condition) throws ClassCastException {
        return this.<T>get(name).filter(v -> condition == null || condition.test(v));
    }

    default <T> T getRaw(String name, CriteriaCondition<T> condition) throws ClassCastException {
        return get(name, condition).orElse(null);
    }

    default <T> T getRawOrDefault(String name, T def) throws ClassCastException {
        return this.<T>get(name).orElse(def);
    }

    default <T> T getRawOrDefault(String name, CriteriaCondition<T> condition, T def) throws ClassCastException {
        return get(name, condition).orElse(def);
    }

    default <T> boolean hasCriteria(String name, CriteriaCondition<T> condition) throws ClassCastException {
        return get(name, condition).isPresent();
    }

    default <T> Optional<T> get(CriteriaDefinition key) throws ClassCastException {
        return get(key.name());
    }

    default <T> Optional<T> get(CriteriaDefinition key, CriteriaCondition<T> condition) throws ClassCastException {
        return get(key.name(), condition);
    }

    default <T> T getRaw(CriteriaDefinition key) throws ClassCastException {
        return this.<T>get(key.name()).orElse(null);
    }

    default <T> T getRaw(CriteriaDefinition key, CriteriaCondition<T> condition) throws ClassCastException {
        return get(key.name(), condition).orElse(null);
    }

    default <T> T getRawOrDefault(CriteriaDefinition key, T def) throws ClassCastException {
        return this.<T>get(key.name()).orElse(def);
    }

    default <T> T getRawOrDefault(CriteriaDefinition key, CriteriaCondition<T> condition, T def) throws ClassCastException {
        return get(key.name(), condition).orElse(def);
    }

    default boolean hasCriteria(CriteriaDefinition def) throws ClassCastException {
        return get(def.name()).isPresent();
    }

    default <T> boolean hasCriteria(CriteriaDefinition def, CriteriaCondition<T> condition) throws ClassCastException {
        return get(def.name(), condition).isPresent();
    }

    default CriteriaCollection addCriteria(CriteriaDefinition def, Object value) throws NullPointerException {
        return addCriteria(def.name(), value);
    }

    default CriteriaCollection removeCriteria(CriteriaDefinition def) throws NullPointerException {
        return removeCriteria(def.name());
    }
}

