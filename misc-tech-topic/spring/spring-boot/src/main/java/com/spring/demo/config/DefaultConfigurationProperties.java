package com.spring.demo.config;

import java.util.Collection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.security.core.GrantedAuthority;

@ConfigurationProperties(prefix = "my-application", ignoreInvalidFields = false, ignoreUnknownFields = true)
public class DefaultConfigurationProperties {

    private final String name;

    private final Collection<GrantedAuthority> authorities;

    @ConstructorBinding
    public DefaultConfigurationProperties(String name, Collection<GrantedAuthority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public String getName() {
        return name;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
