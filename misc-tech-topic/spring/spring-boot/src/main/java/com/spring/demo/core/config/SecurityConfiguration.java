package com.spring.demo.core.config;

import com.spring.demo.core.config.model.DefaultUserDetails;
import com.spring.demo.core.service.PrincipalService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

@Configuration
public class SecurityConfiguration {

    @Order(1)
    @Bean(name = "h2SecurityFilterChain")
    SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/h2-console", "/h2-console/**")
                        .authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll())
                        .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
                        .httpBasic(AbstractHttpConfigurer::disable)
                        .build();
    }

    @Order(2)
    @Bean(name = "apiSecurityFilterChain")
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http)
                    throws Exception {
        return http.securityMatcher("/api/**")
                        .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                        .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
                        .httpBasic(Customizer.withDefaults())
                        .build();
    }

    @Order(3)
    @Bean(name = "uiSecurityFilterChain")
    SecurityFilterChain uiSecurityFilterChain(HttpSecurity http) throws Exception {

        DefaultLoginPageGeneratingFilter defaultLoginPageGeneratingFilter = new DefaultLoginPageGeneratingFilter();
        defaultLoginPageGeneratingFilter.setLogoutSuccessUrl("/ui/logout");
        defaultLoginPageGeneratingFilter.setAuthenticationUrl("/ui/login");
        defaultLoginPageGeneratingFilter.setFailureUrl("/ui/login?error");
        defaultLoginPageGeneratingFilter.setLoginPageUrl("/ui/login");

        defaultLoginPageGeneratingFilter.setUsernameParameter("username");
        defaultLoginPageGeneratingFilter.setPasswordParameter("password");
        defaultLoginPageGeneratingFilter.setFormLoginEnabled(true);

        return http.securityMatcher("/ui/**")
                        .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                        // .authenticationProvider(daoAuthenticationProvider)
                        .httpBasic(AbstractHttpConfigurer::disable)
                        .cors(CorsConfigurer<HttpSecurity>::disable)
                        .csrf(CsrfConfigurer<HttpSecurity>::disable)
                        .formLogin(customizer -> {
                            customizer.loginPage("/ui/login");
                            customizer.defaultSuccessUrl("/ui/");
                            customizer.loginProcessingUrl("/ui/login");
                        })
                        .addFilterBefore(defaultLoginPageGeneratingFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }

    @Order(100)
    @Bean(name = "baseSecurityFilterChain")
    SecurityFilterChain baseSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(customizer -> customizer.anyRequest().permitAll()).build();
    }

    @Bean(name = "userDetailsService")
    UserDetailsService userDetailsService(PrincipalService principalSerivce) {
        return name -> principalSerivce.getPrincipal(name).orElseThrow(() -> new UsernameNotFoundException(name));
    }

    @Bean(name = "userDetailsPasswordService")
    UserDetailsPasswordService userDetailsPasswordService(PrincipalService principalService) {
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
