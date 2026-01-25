package com.spring.demo.core.model;

import com.spring.demo.core.api.CriteriaDefinition;

public record VideoModel(Long id, String name, String description) {

    public enum VideoCriteria implements CriteriaDefinition {

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
        DESCRIPTION {
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
