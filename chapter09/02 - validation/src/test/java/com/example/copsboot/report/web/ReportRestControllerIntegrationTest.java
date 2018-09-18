package com.example.copsboot.report.web;

import com.example.copsboot.infrastructure.SpringProfiles;
import com.example.copsboot.infrastructure.security.SecurityHelperForRestAssured;
import com.example.copsboot.user.UserService;
import com.example.copsboot.user.Users;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

//tag::class[]
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //<1>
@ActiveProfiles(SpringProfiles.TEST)
public class ReportRestControllerIntegrationTest {

    @LocalServerPort
    private int serverport; //<2>

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        RestAssured.port = serverport; //<3>
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); //<4>
    }

    @Test
    public void officerIsUnableToPostAReportIfFileSizeIsTooBig() {

        userService.createOfficer(Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD); //<5>

        String dateTime = "2018-04-11T22:59:03.189+02:00";
        String description = "The suspect is wearing a black hat.";

        SecurityHelperForRestAssured.givenAuthenticatedUser(serverport, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD) //<6>
                                    .when()
                                    .multiPart("image", new MultiPartSpecBuilder(new byte[2_000_000]) //<7>
                                            .fileName("picture.png")
                                            .mimeType("image/png")
                                            .build())
                                    .formParam("dateTime", dateTime)
                                    .formParam("description", description)
                                    .post("/api/reports")
                                    .then()
                                    .statusCode(HttpStatus.BAD_REQUEST.value()); //<8>
    }
}
// end::class[]
