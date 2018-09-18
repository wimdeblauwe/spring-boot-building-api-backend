package com.example.copsboot.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "copsboot-service";

    //tag::resource-server[]
    @Configuration
    @EnableResourceServer //<1>
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll() //<2>
                .and()
                .antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest().authenticated(); //<3>
        }
    }
    //end::resource-server[]

    //tag::authorization-server[]
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private TokenStore tokenStore;

        @Autowired
        private SecurityConfiguration securityConfiguration; //<1>

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security.passwordEncoder(passwordEncoder);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                   .withClient(securityConfiguration.getMobileAppClientId()) //<2>
                   .authorizedGrantTypes("password", "refresh_token")
                   .scopes("mobile_app")
                   .resourceIds(RESOURCE_ID)
                   .secret(passwordEncoder.encode(securityConfiguration.getMobileAppClientSecret())); //<3>
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore)
                     .authenticationManager(authenticationManager)
                     .userDetailsService(userDetailsService);
        }
    }
    //end::authorization-server[]

    //tag::web-security[]
    @Configuration
    public static class WebSecurityGlobalConfig extends WebSecurityConfigurerAdapter {

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

    }
    //end::web-security[]
}
