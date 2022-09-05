package com.assessment.favouriterecipes.integration.common;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles("integration")
public abstract class AbstractIntegrationTest {

    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.5")
            .withLabel("name", "postgres-test-container").withReuse(true);

    @BeforeAll
    public static void beforeAll() {

        container.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);

        registry.add("spring.flyway.url", container::getJdbcUrl);
        registry.add("spring.flyway.user", container::getUsername);
        registry.add("spring.flyway.password", container::getPassword);

        registry.add("spring.datasource.testWhileIdle", () -> Boolean.TRUE);
        registry.add("spring.datasource.test-on-borrow", () -> Boolean.TRUE);

    }

}

