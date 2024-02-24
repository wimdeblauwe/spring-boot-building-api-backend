package com.example.copsboot.user.web;

import com.example.copsboot.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class) //<.>
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc; //<.>

    @MockBean
    private UserService userService;

    @Test
    void givenUnauthenticatedUser_userInfoEndpointReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/me")) //<.>
                .andExpect(status().isUnauthorized()); //<.>
    }

    @Test
    void givenAuthenticatedUser_userInfoEndpointReturnsOk() throws Exception {
        String subject = UUID.randomUUID().toString();
        mockMvc.perform(get("/api/users/me")
                        .with(jwt().jwt(builder -> builder.subject(subject)))) //<.>
                .andExpect(status().isOk()) //<.>
                .andExpect(jsonPath("subject").value(subject)) //<.>
                .andExpect(jsonPath("claims").isMap()) //<.>
                .andDo(print()); //<.>
    }
}
