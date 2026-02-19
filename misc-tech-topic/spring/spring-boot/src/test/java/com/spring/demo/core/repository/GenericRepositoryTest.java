package com.spring.demo.core.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.spring.demo.core.entity.VideoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class GenericRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    VideoRepository videoRepository;

    @Test
    void shouldPersistVideos() {
        VideoEntity entity = new VideoEntity("video1", "descrption1");
        entity = videoRepository.save(entity);

        assertNotNull(entity);
        assertNotNull(entity.getId());

        assertEquals(1L, videoRepository.count());
        assertEquals(entity, videoRepository.getReferenceById(entity.getId()));
    }
}
