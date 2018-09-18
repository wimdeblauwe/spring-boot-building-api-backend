package com.example.copsboot.user.web;

import com.example.copsboot.infrastructure.test.CopsbootControllerTest;
import com.example.copsboot.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//tag::class-annotations[]
@RunWith(SpringRunner.class)
@CopsbootControllerTest(UserRestController.class)
public class UserRestControllerWithResponseBodyValidationTest {
    //end::class-annotations[]
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService service;

    //tag::pwdshort[]
    @Test
    public void testCreateOfficerIfPasswordIsTooShort() throws Exception {
        String email = "wim.deblauwe@example.com";
        String password = "pwd";

        CreateOfficerParameters parameters = new CreateOfficerParameters();
        parameters.setEmail(email);
        parameters.setPassword(password);

        mvc.perform(post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(objectMapper.writeValueAsString(parameters)))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("errors[0].fieldName").value("password")); //<1>

        verify(service, never()).createOfficer(email, password);
    }
    //end::pwdshort[]
}
