package com.spring.demo.core.service;

import com.spring.demo.core.converter.AuthorityConverter;
import com.spring.demo.core.converter.ModelConverter;
import com.spring.demo.core.entity.AuthorityEntity;
import com.spring.demo.core.model.AuthorityModel;
import com.spring.demo.core.repository.AuthorityRepository;
import com.spring.demo.core.repository.EntityRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements ModelService<AuthorityEntity, AuthorityModel> {

    private final AuthorityRepository authorityRepository;
    private final AuthorityConverter authorityConverter;

    public AuthorityService(AuthorityRepository authorityRepository, AuthorityConverter authorityConverter) {
        this.authorityRepository = authorityRepository;
        this.authorityConverter = authorityConverter;
    }

    @Override
    public EntityRepository<AuthorityEntity> repository() {
        return authorityRepository;
    }

    @Override
    public ModelConverter<AuthorityEntity, AuthorityModel> converter() {
        return authorityConverter;
    }

    @Override
    public Class<AuthorityEntity> entityClass() {
        return AuthorityEntity.class;
    }

    @Override
    public Class<AuthorityModel> modelClass() {
        return AuthorityModel.class;
    }
}
