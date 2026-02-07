package com.spring.demo.core.repository;

import com.spring.demo.core.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends EntityRepository<UserEntity> {

    Optional<UserEntity> findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
