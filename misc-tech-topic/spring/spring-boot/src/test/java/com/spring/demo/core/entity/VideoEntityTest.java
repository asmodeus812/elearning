package com.spring.demo.core.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
class VideoEntityTest {

    @Test
    void shouldCreateVideoEntity() {
        VideoEntity userEntity = new VideoEntity();
        assertNotNull(userEntity);
    }

    @Test
    void shouldCreateVideoEntityWithProperties() {
        VideoEntity userEntity = new VideoEntity(1L, "video", "description");

        assertEquals(1L, userEntity.getId());
        assertEquals("video", userEntity.getName());
        assertEquals("description", userEntity.getDescription());
    }

    @Test
    void shouldMutateCreatedVideoEntity() {
        VideoEntity userEntity = new VideoEntity(1L, "video", "description");

        userEntity.setId(2L);
        userEntity.setName("newName");
        userEntity.setDescription("newDescription");

        assertEquals(2L, userEntity.getId());
        assertEquals("newName", userEntity.getName());
        assertEquals("newDescription", userEntity.getDescription());
    }

    @Test
    void shouldEvaluateHashCodeForVideoEntity() {
        VideoEntity userEntity = new VideoEntity(1L, "video", "description");
        assertNotEquals(0, userEntity.hashCode());
    }

    @Test
    void shouldCompareVideoEntityForEquality() {
        VideoEntity userEntity1 = new VideoEntity(1L, "name1", "description");
        VideoEntity userEntity2 = new VideoEntity(1L, "name1", "description");
        VideoEntity userEntity3 = new VideoEntity(3L, "name3", "description");

        assertEquals(userEntity1, userEntity2);
        assertNotSame(userEntity1, userEntity2);

        assertNotEquals(userEntity1, userEntity3);
        assertNotSame(userEntity1, userEntity3);

        assertNotEquals(userEntity2, userEntity3);
        assertNotSame(userEntity2, userEntity3);
    }
}
