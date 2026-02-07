package com.spring.demo.core.repository;

import com.spring.demo.core.entity.VideoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends EntityRepository<VideoEntity> {

    @NonNull
    Page<VideoEntity> findAll(@NonNull Pageable pageable);

    @NonNull
    Optional<VideoEntity> findByName(@NonNull String name);

    @NonNull
    Optional<VideoEntity> findByDescription(@NonNull String name);

    @NonNull
    List<VideoEntity> findAllByNameContaining(@NonNull String name);

    @NonNull
    List<VideoEntity> findAllByDescriptionContaining(@NonNull String name);

    void deleteAllByName(@NonNull String name);
}
