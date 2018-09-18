package com.example.copsboot.infrastructure.security;

import com.example.copsboot.infrastructure.SpringProfiles;
import com.example.copsboot.user.UserService;
import com.example.copsboot.user.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest //<1>
@AutoConfigureMockMvc //<2>
@ActiveProfiles(SpringProfiles.TEST)
public class OAuth2ServerConfigurationTest {

    @Autowired
    private MockMvc mvc; //<3>

    @Autowired
    private UserService userService; //<4>

    @Test
    public void testGetAccessTokenAsOfficer() throws Exception {

        userService.createOfficer(Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD); //<5>

        String clientId = "copsboot-mobile-client";
        String clientSecret = "ccUyb6vS4S8nxfbKPCrN";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("username", Users.OFFICER_EMAIL);
        params.add("password", Users.OFFICER_PASSWORD);

        mvc.perform(post("/oauth/token") //<6>
                            .params(params) //<7>
                            .with(httpBasic(clientId, clientSecret)) //<8>
                            .accept("application/json;charset=UTF-8"))
           .andExpect(status().isOk())
           .andExpect(content().contentType("application/json;charset=UTF-8"))
           .andDo(print()) //<9>
           .andExpect(jsonPath("access_token").isString()) //<10>
           .andExpect(jsonPath("token_type").value("bearer"))
           .andExpect(jsonPath("refresh_token").isString())
           .andExpect(jsonPath("expires_in").isNumber())
           .andExpect(jsonPath("scope").value("mobile_app"))
        ;
    }

}