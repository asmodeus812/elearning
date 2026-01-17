package com.spring.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultStaticAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultStaticAutoConfiguration.class);

    DefaultStaticAutoConfiguration() {
        LOGGER.info("Creating auto configuration bean DefaultStaticAutoConfiguration");
    }

    @Bean
    @ConditionalOnProperty("spring.application.name")
    DefaultAutoConfigBean defaultBeanFromStaticAutoConfig(@Value("${spring.application.name}") String name,
                    DefaultConfigurationProperties props) {
        return new DefaultAutoConfigBean(name);
    }

    public static final class DefaultAutoConfigBean {

        DefaultAutoConfigBean(String name) {
            LOGGER.info("Running DefaultAutoConfigBean constructor with {}", name);
        }
    }
}
