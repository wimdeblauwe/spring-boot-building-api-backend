package com.example.copsboot.user.web;

import com.example.copsboot.infrastructure.SpringProfiles;
import com.example.copsboot.infrastructure.security.OAuth2ServerConfiguration;
import com.example.copsboot.infrastructure.security.SecurityConfiguration;
import com.example.copsboot.infrastructure.security.StubUserDetailsService;
import com.example.copsboot.user.UserService;
import com.example.copsboot.user.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.example.copsboot.infrastructure.security.SecurityHelperForMockMvc.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//tag::webmvctest[]
@RunWith(SpringRunner.class) //<1>
@WebMvcTest(UserRestController.class) //<2>
@ActiveProfiles(SpringProfiles.TEST) //<3>
public class UserRestControllerTest {

    @Autowired
    private MockMvc mvc; //<4>

    @MockBean
    private UserService service; //<5>

    //end::webmvctest[]
    //tag::notauth[]
    @Test
    public void givenNotAuthenticated_whenAskingMyDetails_forbidden() throws Exception {
        mvc.perform(get("/api/users/me")) //<1>
           .andExpect(status().isUnauthorized()); //<2>
    }
    //end::notauth[]

    //tag::authofficer[]
    @Test
    public void givenAuthenticatedAsOfficer_whenAskingMyDetails_detailsReturned() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD); //<1>

        when(service.getUser(Users.officer().getId())).thenReturn(Optional.of(Users.officer())); //<2>

        mvc.perform(get("/api/users/me") //<3>
                            .header(HEADER_AUTHORIZATION, bearer(accessToken))) //<4>
           .andExpect(status().isOk()) //<5>
           .andExpect(jsonPath("id").exists()) //<6>
           .andExpect(jsonPath("email").value(Users.OFFICER_EMAIL))
           .andExpect(jsonPath("roles").isArray())
           .andExpect(jsonPath("roles[0]").value("OFFICER"))
        ;
    }
    //end::authofficer[]

    //tag::authcaptain[]
    @Test
    public void givenAuthenticatedAsCaptain_whenAskingMyDetails_detailsReturned() throws Exception {
        String accessToken = obtainAccessToken(mvc, Users.CAPTAIN_EMAIL, Users.CAPTAIN_PASSWORD);

        when(service.getUser(Users.captain().getId())).thenReturn(Optional.of(Users.captain()));

        mvc.perform(get("/api/users/me")
                            .header(HEADER_AUTHORIZATION, bearer(accessToken)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("id").exists())
           .andExpect(jsonPath("email").value(Users.CAPTAIN_EMAIL))
           .andExpect(jsonPath("roles").isArray())
           .andExpect(jsonPath("roles").value("CAPTAIN"));
    }
    //end::authcaptain[]

    //tag::testconfig[]
    @TestConfiguration //<1>
    @Import(OAuth2ServerConfiguration.class) //<2>
    static class TestConfig {
        @Bean
        public UserDetailsService userDetailsService() {
            return new StubUserDetailsService(); //<3>
        }

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore(); //<4>
        }

        @Bean
        public SecurityConfiguration securityConfiguration() {
            return new SecurityConfiguration(); //<5>
        }

    }
    //end::testconfig[]
}
