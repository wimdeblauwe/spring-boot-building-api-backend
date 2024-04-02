package com.example.copsboot.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class) //<.>
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc; //<.>

    @Test
    void givenUnauthenticatedUser_userInfoEndpointReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/me")) //<.>
                .andExpect(status().isUnauthorized()); //<.>
    }

    @Test
    void givenAuthenticatedUser_userInfoEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/users/me")
                        .with(jwt())) //<.>
                .andExpect(status().isOk()) //<.>
                .andExpect(jsonPath("subject").value("user")) //<.>
                .andExpect(jsonPath("claims").isMap()) //<.>
                .andDo(print()); //<.>
    }
}
