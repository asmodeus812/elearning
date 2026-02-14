package com.spring.demo.core.service;

import com.spring.demo.core.converter.PrincipalConverter;
import com.spring.demo.core.entity.UserEntity;
import com.spring.demo.core.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class PrincipalService {

    private final UserRepository userRepository;

    private final PrincipalConverter principalConverter;

    public PrincipalService(UserRepository userRepository, PrincipalConverter principalConverter) {
        this.userRepository = userRepository;
        this.principalConverter = principalConverter;
    }

    public Optional<UserDetails> getPrincipal() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (Objects.isNull(ctx)) {
            return Optional.empty();
        }
        Authentication authentication = ctx.getAuthentication();
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Optional.ofNullable((UserDetails) authentication.getPrincipal());
    }

    @Transactional
    public Optional<UserDetails> getPrincipal(String username) {
        return userRepository.findByUsername(username).map(principalConverter::convertFrom);
    }

    @Transactional
    public Optional<UserDetails> updatePrincipal(String username, UserDetails details) {
        UserEntity foundUserEntity = userRepository.findByUsername(username).orElseThrow();
        principalConverter.updateEntity(foundUserEntity, details);
        return Optional.ofNullable(principalConverter.convertFrom(foundUserEntity));
    }
}
