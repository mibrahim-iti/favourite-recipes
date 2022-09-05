package com.assessment.favouriterecipes.integration.common;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class IntegrationRepositoryAbstractTest extends AbstractIntegrationTest {

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setup() {

        flyway.clean();
        flyway.migrate();
    }

}

