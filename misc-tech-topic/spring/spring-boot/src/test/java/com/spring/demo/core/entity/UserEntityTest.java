package com.spring.demo.core.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class UserEntityTest {

    @Test
    void shouldCreateUserEntity() {
        UserEntity userEntity = new UserEntity();
        assertNotNull(userEntity);
    }

    @Test
    void shouldCreateUserEntityWithProperties() {
        UserEntity userEntity = new UserEntity(1L, "username", "password", new RoleEntity());

        assertNotNull(userEntity.getRole());
        assertEquals(1L, userEntity.getId());
        assertEquals("username", userEntity.getUsername());
        assertEquals("password", userEntity.getPassword());
    }

    @Test
    void shouldMutateCreatedUserEntity() {
        UserEntity userEntity = new UserEntity(1L, "username", "password", new RoleEntity());

        userEntity.setId(2L);
        userEntity.setUsername("newUsername");
        userEntity.setPassword("newPassword");

        assertNotNull(userEntity.getRole());
        assertEquals(2L, userEntity.getId());
        assertEquals("newUsername", userEntity.getUsername());
        assertEquals("newPassword", userEntity.getPassword());
    }

    @Test
    void shouldEvaluateHashCodeForUserEntity() {
        UserEntity userEntity = new UserEntity(1L, "username", "password", new RoleEntity());
        assertNotEquals(0, userEntity.hashCode());
    }

    @Test
    void shouldCompareUserEntityForEquality() {
        UserEntity userEntity1 = new UserEntity(1L, "username1", "password", new RoleEntity());
        UserEntity userEntity2 = new UserEntity(1L, "username1", "password", new RoleEntity());
        UserEntity userEntity3 = new UserEntity(3L, "username3", "password", new RoleEntity());

        assertEquals(userEntity1, userEntity2);
        assertNotSame(userEntity1, userEntity2);

        assertNotEquals(userEntity1, userEntity3);
        assertNotSame(userEntity1, userEntity3);

        assertNotEquals(userEntity2, userEntity3);
        assertNotSame(userEntity2, userEntity3);
    }
}
