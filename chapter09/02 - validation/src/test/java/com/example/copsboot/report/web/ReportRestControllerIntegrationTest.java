package com.example.copsboot.report.web;

import com.c4_soft.springaddons.security.oauth2.test.webmvc.AddonsWebmvcTestConf;
import com.c4_soft.springaddons.security.oidc.starter.properties.OpenidProviderProperties;
import com.c4_soft.springaddons.security.oidc.starter.properties.SimpleAuthoritiesMappingProperties;
import com.example.copsboot.infrastructure.SpringProfiles;
import com.example.copsboot.user.UserRepository;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.*;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

import static io.restassured.RestAssured.given;

//tag::class[]
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //<.>
@ActiveProfiles(SpringProfiles.INTEGRATION_TEST)
public class ReportRestControllerIntegrationTest {

    private static final String REALM_NAME = "copsboot";
    private static final String ROLE_NAME = "OFFICER";
    private static final String INTEGRATION_TEST_CLIENT_ID = "integration-test-client";
    private static final String TEST_USER_NAME = "wim@example.com";
    private static final String TEST_USER_PASSWORD = "test1234";

    @LocalServerPort
    private int serverport; //<.>

    static KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:22.0.1"); //<.>
    private static String clientSecret;

    @BeforeAll
    static void beforeAll() {
        keycloak.start(); //<.>
        Keycloak client = keycloak.getKeycloakAdminClient(); //<.>

        KeycloakAdminClientFacade clientFacade = new KeycloakAdminClientFacade(client); //<.>
        clientFacade.createRealm(REALM_NAME);
        clientFacade.createRealmRole(REALM_NAME, ROLE_NAME);
        clientFacade.createUser(REALM_NAME, TEST_USER_NAME, TEST_USER_PASSWORD, ROLE_NAME);
        clientSecret = clientFacade.createClient(REALM_NAME, INTEGRATION_TEST_CLIENT_ID);
    }

    @AfterAll
    static void afterAll() {
        keycloak.stop(); //<.>
    }

    @AfterEach
    void afterEach(@Autowired UserRepository userRepository) {
        userRepository.deleteAll();
    }

    // tag::configureProperties[]
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("com.c4-soft.springaddons.oidc.ops[0].iss", () -> keycloak.getAuthServerUrl() + "/realms/" + REALM_NAME); //<.>
        registry.add("com.c4-soft.springaddons.oidc.ops[0].authorities[0].path", () -> "$.realm_access.roles"); //<.>
        registry.add("com.c4-soft.springaddons.oidc.ops[0].authorities[0].prefix", () -> "ROLE_");
    }
    // end::configureProperties[]

    @BeforeEach
    public void setup() {
        RestAssured.port = serverport; //<.>
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); //<.>
    }

    // tag::officerIsUnableToPostAReportIfFileSizeIsTooBig[]
    @Test
    public void officerIsUnableToPostAReportIfFileSizeIsTooBig() {
        String token = getToken(); //<.>

        given()
                .header("Authorization", "Bearer " + token) //<.>
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "mobileToken": "c41536a5a8b9d3f14a7e5472a5322b5e1f76a6e7a9255c2c2e7e0d3a2c5b9d0"
                        }
                         """)
                .post("/api/users") //<.>
                .then()
                .statusCode(HttpStatus.CREATED.value()); //<.>

        given()
                .header("Authorization", "Bearer " + token)
                .multiPart(new MultiPartSpecBuilder(new byte[2_000_000]) //<.>
                        .fileName("picture.png")
                        .controlName("image")
                        .mimeType("image/png")
                        .build())
                .formParam("dateTime", "2018-04-11T22:59:03.189+02:00")
                .formParam("description", "The suspect is wearing a black hat.")
                .formParam("trafficIncident", "false")
                .formParam("numberOfInvolvedCars", "0")
                .when()
                .post("/api/reports")
                .then()
                .statusCode(HttpStatus.PAYLOAD_TOO_LARGE.value()); //<.>
    }
    // end::officerIsUnableToPostAReportIfFileSizeIsTooBig[]

    // tag::getToken[]
    private String getToken() {
        RestTemplate restTemplate = new RestTemplate(); //<.>
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("grant_type", Collections.singletonList("password"));  //<.>
        map.put("client_id", Collections.singletonList(INTEGRATION_TEST_CLIENT_ID));
        map.put("client_secret", Collections.singletonList(clientSecret));
        map.put("username", Collections.singletonList(TEST_USER_NAME));
        map.put("password", Collections.singletonList(TEST_USER_PASSWORD));
        KeycloakToken token =
                restTemplate.postForObject(
                        keycloak.getAuthServerUrl() + "/realms/" + REALM_NAME + "/protocol/openid-connect/token",  //<.>
                        new HttpEntity<>(map, httpHeaders),
                        KeycloakToken.class);

        assert token != null;
        return token.accessToken();  //<.>
    }

    private record KeycloakToken(@JsonProperty("access_token") String accessToken) {  //<.>
    }
    // end::getToken[]
}
// end::class[]
