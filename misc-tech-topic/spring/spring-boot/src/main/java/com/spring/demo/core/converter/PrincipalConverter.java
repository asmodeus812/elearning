package com.spring.demo.core.converter;

import com.spring.demo.core.config.model.DefaultUserDetails;
import com.spring.demo.core.entity.AuthorityEntity;
import com.spring.demo.core.entity.UserEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PrincipalConverter implements ModelConverter<UserEntity, UserDetails> {

    @Override
    public UserEntity convertFrom(UserDetails model) {
        return new UserEntity(model.getUsername(), model.getPassword());
    }

    @Override
    public UserDetails convertFrom(UserEntity entity) {
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                // return entity.getRole()
                // .getAuthorities()
                // .stream()
                // .map(AuthorityEntity::getGrant)
                // .map(SimpleGrantedAuthority::new)
                // .collect(Collectors.toCollection(TreeSet::new));
                return Collections.emptySet();
            }

            @Override
            public String getPassword() {
                return entity.getPassword();
            }

            @Override
            public String getUsername() {
                return entity.getUsername();
            }
        };
    }

    @Override
    public ModelConverter<UserEntity, UserDetails> updateEntity(UserEntity entity, UserDetails model) {
        if (StringUtils.hasText(model.getPassword())) {
            entity.setPassword(model.getPassword());
        }
        return this;
    }

    @Override
    public ModelConverter<UserEntity, UserDetails> updateModel(UserDetails model, UserEntity entity) {
        if (model instanceof DefaultUserDetails) {
            DefaultUserDetails details = (DefaultUserDetails) model;
            if (StringUtils.hasText(entity.getPassword())) {
                details.setPassword(entity.getPassword());
            }
        }
        return this;
    }

}
