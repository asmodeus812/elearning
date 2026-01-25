package com.spring.demo.core.converter;

import com.spring.demo.core.entity.VideoEntity;
import com.spring.demo.core.model.VideoModel;

public class VideoConverter {

    private VideoConverter() {
    }

    public static final VideoModel convertFrom(VideoEntity entity) {
        return new VideoModel(entity.getId(), entity.getName(), entity.getDescription());
    }

    public static final VideoEntity convertFrom(VideoModel model) {
        return new VideoEntity(model.name(), model.description());
    }
}
