package com.example.copsboot.user.web;

import com.example.copsboot.infrastructure.test.CopsbootControllerTest;
import com.example.copsboot.user.UserService;
import com.example.copsboot.user.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static com.example.copsboot.infrastructure.security.SecurityHelperForMockMvc.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//tag::class-annotations[]
@RunWith(SpringRunner.class)
@CopsbootControllerTest(UserRestController.class)
public class UserRestControllerDocumentation {
//end::class-annotations[]
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService service;

    //tag::setup-method[]
    @Autowired
    private WebApplicationContext context; //<1>
    private RestDocumentationResultHandler resultHandler; //<2>

    @Before
    public void setUp() {
        resultHandler = document("{method-name}", //<3>
                                 preprocessRequest(prettyPrint()), //<4>
                                 preprocessResponse(prettyPrint(), //<5>
                                                    removeMatchingHeaders("X.*", //<6>
                                                                          "Pragma",
                                                                          "Expires")));
        mvc = MockMvcBuilders.webAppContextSetup(context) //<7>
                             .apply(springSecurity()) //<8>
                             .apply(documentationConfiguration(restDocumentation)) //<9>
                             .alwaysDo(resultHandler) //<10>
                             .build();
    }
    //end::setup-method[]

    //tag::not-logged-in[]
    @Test
    public void ownUserDetailsWhenNotLoggedInExample() throws Exception {
        mvc.perform(get("/api/users/me"))
           .andExpect(status().isUnauthorized());
    }
    //end::not-logged-in[]

    //tag::officer-details[]
    @Test
    public void authenticatedOfficerDetailsExample() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

        when(service.getUser(Users.officer().getId())).thenReturn(Optional.of(Users.officer()));

        mvc.perform(get("/api/users/me")
                            .header(HEADER_AUTHORIZATION, bearer(accessToken)))
           .andExpect(status().isOk())
           .andDo(resultHandler.document(
                   responseFields(
                           fieldWithPath("id")
                                   .description("The unique id of the user."),
                           fieldWithPath("email")
                                   .description("The email address of the user."),
                           fieldWithPath("roles")
                                   .description("The security roles of the user."))));
    }

    //end::officer-details[]


    //tag::create-officer[]
    @Test
    public void createOfficerExample() throws Exception {
        String email = "wim.deblauwe@example.com";
        String password = "my-super-secret-pwd";

        CreateOfficerParameters parameters = new CreateOfficerParameters(); //<1>
        parameters.setEmail(email);
        parameters.setPassword(password);

        when(service.createOfficer(email, password)).thenReturn(Users.newOfficer(email, password)); //<2>
        when(service.findUserByEmail(email)).thenReturn(Optional.empty());

        mvc.perform(post("/api/users") //<3>
                                       .contentType(MediaType.APPLICATION_JSON_UTF8)
                                       .content(objectMapper.writeValueAsString(parameters))) //<4>
           .andExpect(status().isCreated()) //<5>
           .andDo(resultHandler.document(
                   requestFields( //<6>
                                  fieldWithPath("email")
                                          .description("The email address of the user to be created."),
                                  fieldWithPath("password")
                                          .description("The password for the new user.")
                   ),
                   responseFields( //<7>
                                   fieldWithPath("id")
                                           .description("The unique id of the user."),
                                   fieldWithPath("email")
                                           .description("The email address of the user."),
                                   fieldWithPath("roles")
                                           .description("The security roles of the user."))));
    }
    //end::create-officer[]
}
