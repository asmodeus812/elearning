package com.spring.demo.core.service;

import com.spring.demo.core.converter.ModelConverter;
import com.spring.demo.core.converter.VideoConverter;
import com.spring.demo.core.entity.VideoEntity;
import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.repository.EntityRepository;
import com.spring.demo.core.repository.VideoRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoService extends SearchService<VideoEntity, VideoModel> implements ModelService<VideoEntity, VideoModel> {

    private final VideoRepository videoRepository;

    private final VideoConverter videoConverter;

    public VideoService(VideoRepository videoRepository, VideoConverter videoConverter) {
        this.videoRepository = videoRepository;
        this.videoConverter = videoConverter;
    }

    @Override
    public EntityRepository<VideoEntity> repository() {
        return videoRepository;
    }

    @Override
    public ModelConverter<VideoEntity, VideoModel> converter() {
        return videoConverter;
    }

    @Override
    public Class<VideoEntity> entityClass() {
        return VideoEntity.class;
    }

    @Override
    public Class<VideoModel> modelClass() {
        return VideoModel.class;
    }
}
