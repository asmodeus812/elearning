package com.spring.demo.core.api;

import java.util.function.Function;

@FunctionalInterface
public interface CriteriaTransformer<T, R> extends Function<T, R> {
}
