package com.spring.demo.core.model;

import com.spring.demo.core.api.CriteriaDefinition;
import java.util.Set;

public record RoleModel(Long id, String name, Set<AuthorityModel> authorities) {

    public enum RoleCriteria implements CriteriaDefinition {

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
        AUTHORITIES {
            @Override
            public Class<?> type() {
                return Set.class;
            }
        };

        @Override
        public Class<?> type() {
            return RoleCriteria.class;
        }
    }
}
