package com.spring.demo.core.converter;

import com.spring.demo.core.entity.AuthorityEntity;
import com.spring.demo.core.model.AuthorityModel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AuthorityConverter implements ModelConverter<AuthorityEntity, AuthorityModel> {

    @Override
    public AuthorityEntity convertFrom(AuthorityModel model) {
        return new AuthorityEntity(model.id(), model.name(), model.grant());
    }

    @Override
    public AuthorityModel convertFrom(AuthorityEntity entity) {
        return new AuthorityModel(entity.getId(), entity.getName(), entity.getGrant());
    }

    @Override
    public ModelConverter<AuthorityEntity, AuthorityModel> updateEntity(AuthorityEntity entity, AuthorityModel model) {
        if (StringUtils.hasText(model.name())) {
            entity.setName(model.name());
        }
        if (StringUtils.hasText(model.grant())) {
            entity.setGrant(model.grant());
        }
        return this;
    }

    @Override
    public ModelConverter<AuthorityEntity, AuthorityModel> updateModel(AuthorityModel model, AuthorityEntity entity) {
        return this;
    }

}
