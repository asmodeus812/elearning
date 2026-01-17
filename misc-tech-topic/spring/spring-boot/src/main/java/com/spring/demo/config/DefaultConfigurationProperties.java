package com.spring.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.application", ignoreInvalidFields = false, ignoreUnknownFields = true)
public class DefaultConfigurationProperties {

    private final String name;

    @ConstructorBinding
    public DefaultConfigurationProperties(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
