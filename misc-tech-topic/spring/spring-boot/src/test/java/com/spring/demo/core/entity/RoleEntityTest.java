package com.spring.demo.core.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class RoleEntityTest {

    private static final Collection<AuthorityEntity> AUTHORITIES = Arrays.asList(new AuthorityEntity(1L, "ACTION", "action:model"));

    @Test
    void shouldCreateRoleEntity() {
        RoleEntity userEntity = new RoleEntity();
        assertNotNull(userEntity);
    }

    @Test
    void shouldCreateRoleEntityWithProperties() {
        RoleEntity userEntity = new RoleEntity(1L, "admin", new HashSet<>(AUTHORITIES));

        assertEquals(1L, userEntity.getId());
        assertEquals("admin", userEntity.getName());
        assertEquals(new HashSet<>(AUTHORITIES), userEntity.getAuthorities());
    }

    @Test
    void shouldMutateCreatedRoleEntity() {
        RoleEntity userEntity = new RoleEntity(1L, "admin", new HashSet<>(AUTHORITIES));

        userEntity.setId(2L);
        userEntity.setName("newUsername");
        userEntity.setAuthorities(new HashSet<>(AUTHORITIES));

        assertEquals(2L, userEntity.getId());
        assertEquals("newUsername", userEntity.getName());
        assertEquals(new HashSet<>(AUTHORITIES), userEntity.getAuthorities());
    }

    @Test
    void shouldEvaluateHashCodeForRoleEntity() {
        RoleEntity userEntity = new RoleEntity(1L, "admin", new HashSet<>(AUTHORITIES));
        assertNotEquals(0, userEntity.hashCode());
    }

    @Test
    void shouldCompareRoleEntityForEquality() {
        RoleEntity userEntity1 = new RoleEntity(1L, "name1", new HashSet<>(AUTHORITIES));
        RoleEntity userEntity2 = new RoleEntity(1L, "name1", new HashSet<>(AUTHORITIES));
        RoleEntity userEntity3 = new RoleEntity(3L, "name3", new HashSet<>(AUTHORITIES));

        assertEquals(userEntity1, userEntity2);
        assertNotSame(userEntity1, userEntity2);

        assertNotEquals(userEntity1, userEntity3);
        assertNotSame(userEntity1, userEntity3);

        assertNotEquals(userEntity2, userEntity3);
        assertNotSame(userEntity2, userEntity3);
    }
}
