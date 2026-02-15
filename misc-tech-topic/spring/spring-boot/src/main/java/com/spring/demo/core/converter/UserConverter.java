package com.spring.demo.core.converter;

import com.spring.demo.core.entity.UserEntity;
import com.spring.demo.core.model.UserModel;
import java.util.Objects;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserConverter implements ModelConverter<UserEntity, UserModel> {

    private final RoleConverter roleConverter;

    public UserConverter(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public final UserModel convertFrom(UserEntity entity) {
        return new UserModel(entity.getId(), entity.getUsername(), null, roleConverter.convertFrom(entity.getRole()));
    }

    @Override
    public final UserEntity convertFrom(UserModel model) {
        return new UserEntity(model.id(), model.username(), null);
    }

    @Override
    public ModelConverter<UserEntity, UserModel> updateEntity(UserEntity entity, UserModel model) {
        if (StringUtils.hasText(model.password())) {
            entity.setPassword(model.password());
        }
        if (StringUtils.hasText(model.username())) {
            entity.setPassword(model.username());
        }
        if (!Objects.isNull(model.role())) {
            roleConverter.updateEntity(entity.getRole(), model.role());
        }
        return this;
    }

    @Override
    public ModelConverter<UserEntity, UserModel> updateModel(UserModel model, UserEntity entity) {
        return this;
    }

}
