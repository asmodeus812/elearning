package com.spring.demo.core.config.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class DefaultUserDetails implements UserDetails {

    private String password;

    private final UserDetails delegate;

    public DefaultUserDetails(UserDetails user) {
        this.delegate = user;
        this.password = user.getPassword();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public DefaultUserDetails setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.delegate.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.delegate.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.delegate.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.delegate.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.delegate.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.delegate.isEnabled();
    }
}
