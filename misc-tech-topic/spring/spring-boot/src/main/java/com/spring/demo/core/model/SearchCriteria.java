package com.spring.demo.core.model;

import com.spring.demo.core.api.CriteriaCollection;
import com.spring.demo.core.api.CriteriaDefinition;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SearchCriteria implements CriteriaCollection {

    private static final String THE_CRITERIA_NAME_MESSAGE = "The criteria name can not be null";
    private static final String THE_CRITERIA_VALUE_MESSAGE = "The criteria value can not be null";

    private Map<String, Object> criteria = new HashMap<>();

    private SearchCriteria() {}

    private SearchCriteria(Map<String, Object> criteria) {
        for (Map.Entry<String, Object> values : criteria.entrySet()) {
            addCriteria(values.getKey(), values.getValue());
        }
    }

    public static final CriteriaCollection empty() {
        return new SearchCriteria();
    }

    public static final CriteriaCollection of(Map<String, Object> criteria) {
        Objects.requireNonNull(criteria, "The criteria values can not be empty");
        return new SearchCriteria(criteria);
    }

    public static final SearchCriteria of(Object... keyValuePairs) {
        Objects.requireNonNull(keyValuePairs, "The key value pairs can not be empty");
        assert keyValuePairs.length % 2 == 0 : "Key value pairs inssuficient number";
        SearchCriteria newCriteria = new SearchCriteria();
        for (int i = 0; i < keyValuePairs.length / 2; i += 2) {
            if (keyValuePairs[i] instanceof String) {
                newCriteria.addCriteria((String) keyValuePairs[i], keyValuePairs[i + 1]);
            } else if (keyValuePairs[i] instanceof CriteriaDefinition) {
                newCriteria.addCriteria((CriteriaDefinition) keyValuePairs[i], keyValuePairs[i + 1]);
            } else {
                newCriteria.addCriteria(keyValuePairs[i].toString(), keyValuePairs[i + 1]);
            }
        }
        return newCriteria;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getRaw(String name) throws ClassCastException {
        Objects.requireNonNull(name, THE_CRITERIA_NAME_MESSAGE);
        return (T) criteria.get(name.toLowerCase());
    }

    @Override
    public boolean hasCriteria(String name) throws ClassCastException {
        Objects.requireNonNull(name, THE_CRITERIA_NAME_MESSAGE);
        return criteria.containsKey(name.toLowerCase());
    }

    @Override
    public CriteriaCollection addCriteria(String name, Object value) throws NullPointerException {
        Objects.requireNonNull(name, THE_CRITERIA_NAME_MESSAGE);
        Objects.requireNonNull(value, THE_CRITERIA_VALUE_MESSAGE);
        criteria.put(name.toLowerCase(), value);
        return this;
    }

    @Override
    public CriteriaCollection removeCriteria(String name) throws NullPointerException {
        Objects.requireNonNull(name, THE_CRITERIA_NAME_MESSAGE);
        criteria.remove(name.toLowerCase());
        return this;
    }
}
