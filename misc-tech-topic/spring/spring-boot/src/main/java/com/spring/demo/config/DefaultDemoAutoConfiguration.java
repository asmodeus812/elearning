package com.spring.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultDemoAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDemoAutoConfiguration.class);

    public DefaultDemoAutoConfiguration() {
        LOGGER.info("Creating the auto configuration bean DefaultDemoAutoConfiguration");
    }

    @Bean
    DefaultAutoConfigBean defaultBeanFromAutoConfig(@Value("${spring.application.name}") String name) {
        LOGGER.info("Initializing DefaultAutoConfigBean");
        return new DefaultAutoConfigBean(name);
    }

    public static final class DefaultAutoConfigBean {

        public DefaultAutoConfigBean(String name) {
            LOGGER.info("Running DefaultAutoConfigBean constructor for {}", name);
        }
    }
}
