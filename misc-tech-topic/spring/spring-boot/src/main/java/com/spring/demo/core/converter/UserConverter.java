package com.spring.demo.core.converter;

import com.spring.demo.core.entity.UserEntity;
import com.spring.demo.core.model.UserModel;

public class UserConverter {

    private UserConverter() {}

    public static final UserModel convertFrom(UserEntity entity) {
        return new UserModel(entity.getId(), entity.getUsername());
    }

    public static final UserEntity convertFrom(UserModel model) {
        return new UserEntity(model.id(), model.username(), null);
    }
}
