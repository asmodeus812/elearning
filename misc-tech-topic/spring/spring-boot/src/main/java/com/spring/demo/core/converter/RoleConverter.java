package com.spring.demo.core.converter;

import com.spring.demo.core.entity.RoleEntity;
import com.spring.demo.core.model.RoleModel;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class RoleConverter implements ModelConverter<RoleEntity, RoleModel> {

    private final AuthorityConverter authorityConverter;

    public RoleConverter(AuthorityConverter authorityConverter) {
        this.authorityConverter = authorityConverter;
    }

    @Override
    public RoleEntity convertFrom(RoleModel model) {
        return new RoleEntity(model.id(), model.name(),
                        model.authorities().stream().map(authorityConverter::convertFrom).collect(Collectors.toSet()));
    }

    @Override
    public RoleModel convertFrom(RoleEntity entity) {
        return new RoleModel(entity.getId(), entity.getName(),
                        entity.getAuthorities().stream().map(authorityConverter::convertFrom).collect(Collectors.toSet()));
    }

    @Override
    public ModelConverter<RoleEntity, RoleModel> updateEntity(RoleEntity entity, RoleModel model) {
        if (StringUtils.hasText(model.name())) {
            entity.setName(model.name());
        }
        if (!CollectionUtils.isEmpty(model.authorities())) {
            entity.setAuthorities(model.authorities().stream().map(authorityConverter::convertFrom).collect(Collectors.toSet()));
        }
        return this;
    }

    @Override
    public ModelConverter<RoleEntity, RoleModel> updateModel(RoleModel entity, RoleEntity model) {
        return this;
    }
}
