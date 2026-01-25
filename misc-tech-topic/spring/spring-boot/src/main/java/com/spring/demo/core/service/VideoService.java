package com.spring.demo.core.service;

import com.spring.demo.core.api.CriteriaCollection;
import com.spring.demo.core.api.CriteriaCondition;
import com.spring.demo.core.api.CriteriaPipeline;
import com.spring.demo.core.converter.VideoConverter;
import com.spring.demo.core.entity.VideoEntity;
import com.spring.demo.core.model.VideoModel;
import com.spring.demo.core.repository.VideoRepository;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import jakarta.transaction.Transactional;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Page<VideoModel> getVideos(Pageable pageable) {
        return videoRepository.findAll(pageable).map(VideoConverter::convertFrom);
    }

    public List<VideoModel> getVideos(CriteriaCollection search) {
        return CriteriaPipeline.<List<VideoEntity>>of(search)
                        .<String>accept(VideoModel.VideoCriteria.NAME, StringUtils::hasText,
                                        v -> videoRepository.findAllByNameContaining(v))
                        .<String>accept(VideoModel.VideoCriteria.DESCRIPTION, StringUtils::hasText,
                                        v -> videoRepository.findAllByDescriptionContaining(v))
                        .<Long>accept(VideoModel.VideoCriteria.ID, v -> videoRepository.findAll())
                        .limitFirst()
                        .flatMap(Collection::stream)
                        .map(VideoConverter::convertFrom)
                        .toList();
    }

    public Optional<VideoModel> getVideo(CriteriaCollection search) {
        return CriteriaPipeline.<VideoEntity>of(search)
                        .<String>acceptOptional(VideoModel.VideoCriteria.NAME, StringUtils::hasText, v -> videoRepository.findByName(v))
                        .<String>acceptOptional(VideoModel.VideoCriteria.DESCRIPTION, StringUtils::hasText,
                                        v -> videoRepository.findByDescription(v))
                        .<Long>acceptOptional(VideoModel.VideoCriteria.ID, CriteriaCondition.<Long>not(Objects::isNull),
                                        v -> videoRepository.findById(v))
                        .findFirst()
                        .map(VideoConverter::convertFrom);
    }

    @Transactional
    public Optional<VideoModel> updateVideo(VideoModel target) {
        Optional<VideoEntity> targetFoundVideoEntity = videoRepository.findById(target.id());
        if (targetFoundVideoEntity.isPresent()) {
            if (StringUtils.hasText(target.name())) {
                targetFoundVideoEntity.get().setName(target.name());
            }
            if (StringUtils.hasText(target.description())) {
                targetFoundVideoEntity.get().setDescription(target.description());
            }
            VideoEntity targetSavedVideoEntity = videoRepository.save(targetFoundVideoEntity.get());
            return Optional.of(VideoConverter.convertFrom(targetSavedVideoEntity));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteVideos() {
        videoRepository.deleteAllInBatch();
        return videoRepository.count() == 0;
    }

    @Transactional
    public boolean deleteVideo(Long target) {
        videoRepository.deleteById(target);
        return videoRepository.findById(target).isPresent();
    }

    @Transactional
    public VideoModel addVideo(VideoModel video) {
        VideoEntity newSavedVideo = new VideoEntity(video.name(), video.description());
        newSavedVideo = videoRepository.save(newSavedVideo);
        return VideoConverter.convertFrom(newSavedVideo);
    }
}
