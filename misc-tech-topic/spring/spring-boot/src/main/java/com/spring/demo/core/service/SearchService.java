package com.spring.demo.core.service;

import com.spring.demo.core.api.CriteriaCollection;
import com.spring.demo.core.api.CriteriaCondition;
import com.spring.demo.core.api.CriteriaDefinition;
import com.spring.demo.core.api.CriteriaPipeline;
import com.spring.demo.core.converter.ModelConverter;
import com.spring.demo.core.entity.AbstractEntity;
import com.spring.demo.core.repository.EntityRepository;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Example;
import org.springframework.util.StringUtils;

public abstract class SearchService<E extends AbstractEntity, M> {

    abstract EntityRepository<E> repository();

    abstract ModelConverter<E, M> converter();

    abstract Class<E> entityClass();

    abstract Class<M> modelClass();

    public Collection<M> search(CriteriaCollection criteria, Enum<? extends CriteriaDefinition>[] enumerators) {
        Map<String, Method> setters = getTypeSetters(entityClass());
        CriteriaPipeline<E> pipeline = CriteriaPipeline.of(criteria);
        ConversionService conversionService = DefaultConversionService.getSharedInstance();

        E probe;
        try {
            probe = entityClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
            return Collections.emptyList();
        }
        for (Enum<? extends CriteriaDefinition> key : enumerators) {
            String property = normalizeKey(key.name());
            Method setter = setters.get(property);
            if (Objects.isNull(setter)) {
                continue;
            }

            if (!(key instanceof CriteriaDefinition)) {
                throw new IllegalStateException(String.format("Provided criterion key is of invalid type %s, expected %s",
                                key.getClass().getSimpleName(), CriteriaDefinition.class.getSimpleName()));
            }

            Class<?> paramType = setter.getParameterTypes()[0];
            CriteriaDefinition definition = (CriteriaDefinition) key;
            if (!definition.type().equals(paramType)) {
                throw new IllegalStateException(String.format("Type mismatch between definition %s and expected type %s ",
                                definition.type().getSimpleName(), paramType.getSimpleName()));
            }

            CriteriaCondition<Object> condition = defaultCondition(paramType);
            pipeline.accept(definition, condition, raw -> {
                Object arg = convertValue(conversionService, paramType, raw);
                return invokeSetter(setter, probe, arg);
            });
        }

        Example<E> example = pipeline.findFirst().map(Example::of).orElseThrow();
        return repository().findAll(example).stream().map(converter()::convertFrom).toList();
    }

    private Map<String, Method> getTypeSetters(Class<?> type) {
        try {
            return Arrays.stream(Introspector.getBeanInfo(type).getPropertyDescriptors())
                            .filter(pd -> pd.getWriteMethod() != null)
                            .collect(Collectors.toMap(pd -> pd.getName(), pd -> pd.getWriteMethod()));
        } catch (Exception e) {
            throw new IllegalStateException(String.format("Failed introspecting setters for %s", type.getSimpleName()), e);
        }
    }

    private CriteriaCondition<Object> defaultCondition(Class<?> paramType) {
        if (String.class.equals(paramType)) {
            return v -> (v instanceof String s) && StringUtils.hasText(s);
        }
        return Objects::nonNull;
    }

    private Object convertValue(ConversionService conversionService, Class<?> paramType, Object raw) {
        return (Objects.isNull(raw) || paramType.isInstance(raw)) ? raw : conversionService.convert(raw, paramType);
    }

    private E invokeSetter(Method setter, E target, Object arg) {
        try {
            setter.invoke(target, arg);
            return target;
        } catch (Exception e) {
            throw new IllegalArgumentException(
                            String.format("Failed invoking %s on %s", setter.getName(), target.getClass().getSimpleName()), e);
        }
    }

    private String normalizeKey(String keyName) {
        String lowerCase = keyName.trim().toLowerCase();
        if (!lowerCase.contains("_")) {
            return lowerCase;
        }
        String[] parts = lowerCase.split("_+");
        StringBuilder finalKeyName = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].isEmpty()) {
                continue;
            }
            finalKeyName.append(Character.toUpperCase(parts[i].charAt(0))).append(parts[i].substring(1));
        }
        return finalKeyName.toString();
    }
}
