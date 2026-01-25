package com.spring.demo.core.repository;

import com.spring.demo.core.entity.VideoEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    Optional<VideoEntity> findByName(String name);

    Optional<VideoEntity> findByDescription(String name);

    List<VideoEntity> findAllLikeName(String name);

    List<VideoEntity> findAllLikeDescription(String name);

    void deleteAllByName(String name);
}
