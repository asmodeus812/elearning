package com.spring.demo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.spring.demo")
public class DemoApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Starting the spring boot application");
        SpringApplication.run(DemoApplication.class, args);
        LOGGER.info("Returning the spring boot application");
    }
}
