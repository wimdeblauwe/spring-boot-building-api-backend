package com.example.copsboot.infrastructure.security;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecurityHelperForMockMvc {

    private static final String UNIT_TEST_CLIENT_ID = "test-client-id"; //<1>
    private static final String UNIT_TEST_CLIENT_SECRET = "test-client-secret"; //<2>

    public static final String HEADER_AUTHORIZATION = "Authorization";

    /**
     * Allows to get an access token for the given user in the context of a spring (unit) test
     * using MockMVC.
     *
     * @param mvc      the MockMvc instance
     * @param username the username
     * @param password the password
     * @return the <code>access_token</code> to be used in the <code>Authorization</code> header
     * @throws Exception if no token could be obtained.
     */
    public static String obtainAccessToken(MockMvc mvc, String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", UNIT_TEST_CLIENT_ID);
        params.add("client_secret", UNIT_TEST_CLIENT_SECRET);
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mvc.perform(post("/oauth/token")
                                      .params(params)
                                      .with(httpBasic(UNIT_TEST_CLIENT_ID, UNIT_TEST_CLIENT_SECRET))
                                      .accept("application/json;charset=UTF-8"))
                     .andExpect(status().isOk())
                     .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    public static String bearer(String accessToken) {
        return "Bearer " + accessToken;
    }
}