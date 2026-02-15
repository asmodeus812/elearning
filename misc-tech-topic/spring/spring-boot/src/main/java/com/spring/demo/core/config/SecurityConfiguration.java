package com.spring.demo.core.config;

import com.spring.demo.core.config.model.MutableUserDetails;
import com.spring.demo.core.service.PrincipalService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableMethodSecurity
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
    SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher("/api/**")
                        .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                        .formLogin(FormLoginConfigurer<HttpSecurity>::disable)
                        .cors(CorsConfigurer<HttpSecurity>::disable)
                        .csrf(CsrfConfigurer<HttpSecurity>::disable)
                        .httpBasic(Customizer.withDefaults())
                        .build();
    }

    @Order(3)
    @Bean(name = "uiSecurityFilterChain")
    SecurityFilterChain uiSecurityFilterChain(HttpSecurity http) throws Exception {

        DefaultLoginPageGeneratingFilter defaultLoginPageGeneratingFilter = new DefaultLoginPageGeneratingFilter();
        defaultLoginPageGeneratingFilter.setResolveHiddenInputs(this::hiddenInputs);
        defaultLoginPageGeneratingFilter.setLogoutSuccessUrl("/ui/logout");
        defaultLoginPageGeneratingFilter.setAuthenticationUrl("/ui/login");
        defaultLoginPageGeneratingFilter.setFailureUrl("/ui/login?error");
        defaultLoginPageGeneratingFilter.setLoginPageUrl("/ui/login");

        defaultLoginPageGeneratingFilter.setUsernameParameter("username");
        defaultLoginPageGeneratingFilter.setPasswordParameter("password");
        defaultLoginPageGeneratingFilter.setFormLoginEnabled(true);

        CorsConfiguration configurationSource = new CorsConfiguration();
        configurationSource.setAllowedOrigins(List.of("http://www.localhost:8080"));
        configurationSource.setAllowedMethods(List.of("GET", "POST", "DELETE", "OPTIONS"));
        configurationSource.setAllowedHeaders(List.of("Content-Type", "X-XSRF-TOKEN"));
        configurationSource.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/ui/**", configurationSource);

        return http.securityMatcher("/ui/**")
                        .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                        .httpBasic(AbstractHttpConfigurer::disable)
                        .cors(config -> config.configurationSource(source))
                        .csrf(config -> config.csrfTokenRepository(new CookieCsrfTokenRepository()))
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
        return (user, password) -> {
            String username = user.getUsername();
            MutableUserDetails userDetails = new MutableUserDetails(user);
            userDetails.setPassword(password);
            Optional<UserDetails> result = principalService.updatePrincipal(username, userDetails);
            return result.orElseThrow(() -> new UsernameNotFoundException(username));
        };
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

    private Map<String, String> hiddenInputs(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return (token != null) ? Collections.singletonMap(token.getParameterName(), token.getToken()) : Collections.emptyMap();
    }
}
