package com.spring.demo.core.config.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MutableUserDetails implements UserDetails {

    private String password;

    private final UserDetails delegate;

    public MutableUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.delegate = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public String getPassword() {
                return getPassword();
            }

            @Override
            public String getUsername() {
                return username;
            }
        };
        this.password = password;
    }

    public MutableUserDetails(UserDetails user) {
        this.delegate = user;
        this.password = user.getPassword();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public MutableUserDetails setPassword(String password) {
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
