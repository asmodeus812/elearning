package com.spring.demo.core.model;

import com.spring.demo.core.api.CriteriaDefinition;

public record UserModel(Long id, String username, String password, RoleModel role) {

    public enum UserCriteria implements CriteriaDefinition {

        ID {
            @Override
            public Class<?> type() {
                return Long.class;
            }
        },
        USERNAME {
            @Override
            public Class<?> type() {
                return String.class;
            }
        },
        PASSWORD {
            @Override
            public Class<?> type() {
                return String.class;
            }
        },
        ROLE {
            @Override
            public Class<?> type() {
                return RoleModel.class;
            }
        };

        @Override
        public Class<?> type() {
            throw new UnsupportedOperationException();
        }
    }
}
