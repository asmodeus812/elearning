package com.spring.demo.core.repository;

import com.spring.demo.core.entity.RoleEntity;
import java.util.Optional;

public interface RoleRepository extends EntityRepository<RoleEntity> {

    Optional<RoleEntity> findByName(String name);
}
