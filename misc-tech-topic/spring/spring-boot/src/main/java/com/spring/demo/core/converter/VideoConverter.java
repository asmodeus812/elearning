package com.spring.demo.core.converter;

import com.spring.demo.core.entity.VideoEntity;
import com.spring.demo.core.model.VideoModel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VideoConverter implements ModelConverter<VideoEntity, VideoModel> {

    private VideoConverter() {}

    @Override
    public final VideoModel convertFrom(VideoEntity entity) {
        return new VideoModel(entity.getId(), entity.getName(), entity.getDescription());
    }

    @Override
    public final VideoEntity convertFrom(VideoModel model) {
        return new VideoEntity(model.name(), model.description());
    }

    @Override
    public ModelConverter<VideoEntity, VideoModel> updateEntity(VideoEntity entity, VideoModel model) {
        if (StringUtils.hasText(model.name())) {
            entity.setName(model.name());
        }
        if (StringUtils.hasText(model.description())) {
            entity.setDescription(model.description());
        }
        return this;
    }

    @Override
    public ModelConverter<VideoEntity, VideoModel> updateModel(VideoModel model, VideoEntity entity) {
        return this;
    }
}
