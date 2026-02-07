package com.spring.demo.core.model;

import com.spring.demo.core.api.CriteriaDefinition;

public record AuthorityModel(Long id, String name, String grant) {

    public enum AuthorityCriteria implements CriteriaDefinition {

        ID {
            @Override
            public Class<?> type() {
                return Long.class;
            }
        },
        NAME {
            @Override
            public Class<?> type() {
                return String.class;
            }
        },
        GRANT {
            @Override
            public Class<?> type() {
                return String.class;
            }
        };

        @Override
        public Class<?> type() {
            throw new UnsupportedOperationException();
        }
    }
}

