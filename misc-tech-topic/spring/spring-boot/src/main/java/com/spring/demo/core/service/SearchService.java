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
        Map<String, Method> setters = settersByPropertyName(entityClass());
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
            String prop = normalizeKey(key.name());
            Method setter = setters.get(prop);
            if (setter == null)
                continue;

            Class<?> paramType = setter.getParameterTypes()[0];
            CriteriaCondition<Object> condition = defaultCondition(paramType);

            pipeline.accept((CriteriaDefinition) key, condition, raw -> {
                Object arg = (raw == null || paramType.isInstance(raw)) ? raw : conversionService.convert(raw, paramType);
                invokeSetter(setter, probe, arg);
                return probe;
            });
        }
        Example<E> example = pipeline.findFirst().map(Example::of).orElseThrow();
        return repository().findAll(example).stream().map(converter()::convertFrom).toList();
    }

    private CriteriaCondition<Object> defaultCondition(Class<?> paramType) {
        if (String.class.equals(paramType)) {
            return v -> (v instanceof String s) && StringUtils.hasText(s);
        }
        return Objects::nonNull;
    }

    private void invokeSetter(Method setter, Object target, Object arg) {
        try {
            setter.invoke(target, arg);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed invoking " + setter.getName() + " on " + target.getClass().getSimpleName(), e);
        }
    }

    private Map<String, Method> settersByPropertyName(Class<?> type) {
        try {
            return Arrays.stream(Introspector.getBeanInfo(type).getPropertyDescriptors())
                            .filter(pd -> pd.getWriteMethod() != null)
                            .collect(Collectors.toMap(pd -> pd.getName(), pd -> pd.getWriteMethod()));
        } catch (Exception e) {
            throw new IllegalStateException("Failed introspecting setters for " + type.getName(), e);
        }
    }

    private String normalizeKey(String enumName) {
        String s = enumName.trim().toLowerCase();
        if (!s.contains("_"))
            return s;
        String[] parts = s.split("_+");
        StringBuilder out = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].isEmpty())
                continue;
            out.append(Character.toUpperCase(parts[i].charAt(0))).append(parts[i].substring(1));
        }
        return out.toString();
    }
}
