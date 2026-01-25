package com.spring.demo.core.api;

import java.util.Objects;
import java.util.function.Predicate;

@FunctionalInterface
public interface CriteriaCondition<T> extends Predicate<T> {

    @Override
    default CriteriaCondition<T> negate() {
        return this.negate();
    }

    @SuppressWarnings("unchecked")
    static <T> CriteriaCondition<T> not(CriteriaCondition<? super T> target) {
        Objects.requireNonNull(target);
        return (CriteriaCondition<T>) target.negate();
    }
}
