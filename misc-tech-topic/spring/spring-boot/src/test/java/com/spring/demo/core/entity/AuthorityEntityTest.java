package com.spring.demo.core.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class AuthorityEntityTest {

    @Test
    void shouldCreateAuthorityEntity() {
        AuthorityEntity userEntity = new AuthorityEntity();
        assertNotNull(userEntity);
    }

    @Test
    void shouldCreateAuthorityEntityWithProperties() {
        AuthorityEntity userEntity = new AuthorityEntity(1L, "name", "grant");

        assertEquals(1L, userEntity.getId());
        assertEquals("name", userEntity.getName());
        assertEquals("grant", userEntity.getGrant());
    }

    @Test
    void shouldMutateCreatedAuthorityEntity() {
        AuthorityEntity userEntity = new AuthorityEntity(1L, "name", "grant");

        userEntity.setId(2L);
        userEntity.setName("newName");
        userEntity.setGrant("newGrant");

        assertEquals(2L, userEntity.getId());
        assertEquals("newName", userEntity.getName());
        assertEquals("newGrant", userEntity.getGrant());
    }

    @Test
    void shouldEvaluateHashCodeForAuthorityEntity() {
        AuthorityEntity userEntity = new AuthorityEntity(1L, "name", "grant");
        assertNotEquals(0, userEntity.hashCode());
    }

    @Test
    void shouldCompareAuthorityEntityForEquality() {
        AuthorityEntity userEntity1 = new AuthorityEntity(1L, "name1", "grant");
        AuthorityEntity userEntity2 = new AuthorityEntity(1L, "name1", "grant");
        AuthorityEntity userEntity3 = new AuthorityEntity(3L, "name3", "grant");

        assertEquals(userEntity1, userEntity2);
        assertNotSame(userEntity1, userEntity2);

        assertNotEquals(userEntity1, userEntity3);
        assertNotSame(userEntity1, userEntity3);

        assertNotEquals(userEntity2, userEntity3);
        assertNotSame(userEntity2, userEntity3);
    }
}
