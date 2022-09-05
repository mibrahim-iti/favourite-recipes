package com.assessment.favouriterecipes.integration.rest;

import com.assessment.favouriterecipes.integration.common.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ActuatorTest extends AbstractIntegrationTest {

    @LocalServerPort
    protected int localServerPort;

    @Test
    void healthy() {

        final var requestSpecification = new RequestSpecBuilder().setPort(localServerPort)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

        given(requestSpecification)
                .when()
                .get("/actuator/health")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().ifValidationFails(LogDetail.ALL);
    }

}
