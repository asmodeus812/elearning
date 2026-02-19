package com.spring.demo.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.spring.demo.core.converter.VideoConverter;
import com.spring.demo.core.repository.VideoRepository;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@Testable
@SpringJUnitConfig
@Import(VideoService.class)
class VideoServiceTest {

    @MockitoBean
    VideoRepository videoRepository;

    @MockitoBean
    VideoConverter videoConverter;

    @Autowired
    VideoService videoService;

    @Test
    void shouldCreateVideoService() {
        assertNotNull(videoService);
        assertNotNull(videoService.entityClass());
        assertNotNull(videoService.modelClass());

        assertNotNull(videoService.repository());
        assertNotNull(videoService.converter());
    }
}
