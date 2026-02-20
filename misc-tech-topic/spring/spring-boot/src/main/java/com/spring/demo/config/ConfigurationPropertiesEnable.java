package com.spring.demo.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "com.spring.demo")
public class ConfigurationPropertiesEnable {

    @Bean
    @ConfigurationPropertiesBinding
    Converter<String, GrantedAuthority> grantedAuthorityConverter() {
        return new Converter<String, GrantedAuthority>() {
            @Override
            @Nullable
            public GrantedAuthority convert(String source) {
                return new SimpleGrantedAuthority(source);
            }
        };
    }
}
