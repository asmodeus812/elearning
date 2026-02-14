package com.spring.demo.core.service;

import com.spring.demo.core.converter.ModelConverter;
import com.spring.demo.core.converter.UserConverter;
import com.spring.demo.core.entity.UserEntity;
import com.spring.demo.core.model.UserModel;
import com.spring.demo.core.repository.EntityRepository;
import com.spring.demo.core.repository.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UserService extends SearchService<UserEntity, UserModel> implements ModelService<UserEntity, UserModel> {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public EntityRepository<UserEntity> repository() {
        return userRepository;
    }

    @Override
    public ModelConverter<UserEntity, UserModel> converter() {
        return userConverter;
    }

    @Override
    public Class<UserEntity> entityClass() {
        return UserEntity.class;
    }

    @Override
    public Class<UserModel> modelClass() {
        return UserModel.class;
    }

    @Transactional
    public Optional<UserModel> findByUsername(String username) {
        Optional<UserEntity> entity = userRepository.findByUsername(username);
        return entity.map(converter()::convertFrom);
    }
}
