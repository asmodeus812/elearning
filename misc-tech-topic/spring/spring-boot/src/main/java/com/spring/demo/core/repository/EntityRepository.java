package com.spring.demo.core.repository;

import com.spring.demo.core.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository<T extends AbstractEntity> extends JpaRepository<T, Long> {
}
