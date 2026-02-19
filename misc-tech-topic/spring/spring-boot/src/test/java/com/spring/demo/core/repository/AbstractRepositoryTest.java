package com.spring.demo.core.repository;

import org.junit.platform.commons.annotation.Testable;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testable
@DataJpaTest
@Testcontainers
@ContextConfiguration(initializers = AbstractRepositoryTest.class)
@AutoConfigureTestDatabase(replace = Replace.NONE, connection = EmbeddedDatabaseConnection.NONE)
public class AbstractRepositoryTest implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    static final PostgreSQLContainer<?> postgresDatabase = new PostgreSQLContainer<>("postgres:16-alpine");

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext,
                        "spring.datasource.url=" + postgresDatabase.getJdbcUrl(),
                        "spring.datasource.username=" + postgresDatabase.getUsername(),
                        "spring.datasource.password=" + postgresDatabase.getPassword(),
                        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
                        "spring.datasource.driverClassName=org.postgresql.Driver", "spring.jpa.hibernate.ddl-auto=create-drop");
    }
}
