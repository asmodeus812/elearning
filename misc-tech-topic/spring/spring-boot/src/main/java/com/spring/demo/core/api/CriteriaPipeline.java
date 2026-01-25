package com.spring.demo.core.api;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CriteriaPipeline<R> {

    private final CriteriaCollection searchCriteria;

    private final Stream.Builder<CriteriaTuple<?, R>> builder = Stream.builder();

    private CriteriaPipeline(CriteriaCollection searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public static final <R> CriteriaPipeline<R> of(CriteriaCollection searchCriteria) {
        return new CriteriaPipeline<>(searchCriteria);
    }

    public <T> CriteriaPipeline<R> accept(CriteriaDefinition def, CriteriaTransformer<T, R> transformer) {
        builder.add(new CriteriaTuple<T, R>(def, v -> true, transformer));
        return this;
    }

    public <T> CriteriaPipeline<R> acceptOptional(CriteriaDefinition def, CriteriaTransformer<T, Optional<R>> transformer) {
        builder.add(new CriteriaTuple<T, R>(def, v -> true, v -> transformer.apply(v).orElse(null)));
        return this;
    }


    public <T> CriteriaPipeline<R> accept(CriteriaDefinition def, CriteriaCondition<T> cond, CriteriaTransformer<T, R> transformer) {
        builder.add(new CriteriaTuple<T, R>(def, cond, transformer));
        return this;
    }

    public <T> CriteriaPipeline<R> acceptOptional(CriteriaDefinition def, CriteriaCondition<T> cond,
                    CriteriaTransformer<T, Optional<R>> transformer) {
        builder.add(new CriteriaTuple<T, R>(def, cond, v -> transformer.apply(v).orElse(null)));
        return this;
    }

    public Stream<R> stream() {
        return builder.build()
                        .filter(tuple -> searchCriteria.hasCriteria(tuple.pass(), tuple::test))
                        .map(tuple -> tuple.apply(searchCriteria.getRawOrDefault(tuple.definition, null)));
    }

    public Stream<R> limitFirst() {
        return stream().limit(1);
    }

    public Stream<R> limitAnu() {
        return stream().limit(1);
    }

    public Optional<R> findFirst() {
        return stream().findFirst();
    }

    public Optional<R> findAny() {
        return stream().findAny();
    }

    public List<R> toList() {
        return stream().collect(Collectors.toList());
    }

    public Set<R> toSet() {
        return stream().collect(Collectors.toSet());
    }

    public long count() {
        return stream().count();
    }

    private static final class CriteriaTuple<T, R> {

        private final CriteriaDefinition definition;

        private final CriteriaCondition<T> condition;

        private final CriteriaTransformer<T, R> transformer;

        private CriteriaTuple(CriteriaDefinition definition, CriteriaCondition<T> condition, CriteriaTransformer<T, R> transformer) {
            this.definition = definition;
            this.condition = condition;
            this.transformer = transformer;
        }

        String pass() {
            return Optional.ofNullable(definition).map(CriteriaDefinition::name).orElse("");
        }

        boolean test(T value) {
            return Optional.ofNullable(condition).map(c -> c.test(value)).orElse(false);
        }

        R apply(T value) {
            return Optional.ofNullable(transformer).map(t -> t.apply(value)).orElse(null);
        }
    }
}
