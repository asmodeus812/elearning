package com.spring.demo.core.service;

import com.spring.demo.core.converter.ModelConverter;
import com.spring.demo.core.converter.RoleConverter;
import com.spring.demo.core.entity.RoleEntity;
import com.spring.demo.core.model.RoleModel;
import com.spring.demo.core.repository.EntityRepository;
import com.spring.demo.core.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements ModelService<RoleEntity, RoleModel> {

    private final RoleRepository roleRepository;

    private final RoleConverter roleConverter;

    public RoleService(RoleRepository roleRepository, RoleConverter roleConverter) {
        this.roleRepository = roleRepository;
        this.roleConverter = roleConverter;
    }

    @Override
    public EntityRepository<RoleEntity> repository() {
        return roleRepository;
    }

    @Override
    public ModelConverter<RoleEntity, RoleModel> converter() {
        return roleConverter;
    }

    @Override
    public Class<RoleEntity> entityClass() {
        return RoleEntity.class;
    }

    @Override
    public Class<RoleModel> modelClass() {
        return RoleModel.class;
    }
}
