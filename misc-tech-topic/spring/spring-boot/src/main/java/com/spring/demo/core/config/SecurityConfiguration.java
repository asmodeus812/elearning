package com.spring.demo.core.config;

import com.spring.demo.core.config.model.DefaultUserDetails;
import com.spring.demo.core.service.PrincipalService;
import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean(name = "securityFilterChain")
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(Customizer.withDefaults())
            http.formLogin(null)
        return http.build();
    }

    @Bean(name = "userDetailsService")
    UserDetailsService defaultUserDetailsService(PrincipalService principalSerivce) {
        return name -> principalSerivce.getPrincipal(name).orElseThrow(() -> new UsernameNotFoundException(name));
    }

    @Bean(name = "userDetailsPasswordService")
    UserDetailsPasswordService defaultUserDetailsPasswordService(PrincipalService principalService) {
        return (user, password) -> principalService.updatePrincipal(user.getUsername(), new DefaultUserDetails(user).setPassword(password))
                        .orElseThrow(() -> new UsernameNotFoundException(user.getUsername()));
    }

    @Profile("!local")
    @Bean(name = "passwordEncoder")
    PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Profile("local")
    @Bean(name = "passwordEncoder")
    PasswordEncoder localPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword == null ? null : rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String storedPassword) {
                return Objects.equals(rawPassword == null ? null : rawPassword.toString(), storedPassword);
            }
        };
    }
}
