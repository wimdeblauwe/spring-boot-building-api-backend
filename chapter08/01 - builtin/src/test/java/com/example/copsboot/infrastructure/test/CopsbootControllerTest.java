package com.example.copsboot.infrastructure.test;

import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import com.example.copsboot.infrastructure.security.WebSecurityConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Custom annotation for all {@link org.springframework.stereotype.Controller Controller} tests on the project. By using
 * this single annotation, everything is configured properly to test a controller:
 */
//tag::class[]
@Retention(RetentionPolicy.RUNTIME) //<.>
@WebMvcTest //<.>
@AutoConfigureAddonsWebmvcResourceServerSecurity //<.>
@Import(WebSecurityConfiguration.class) //<.>
public @interface CopsbootControllerTest {

    @AliasFor(annotation = WebMvcTest.class, attribute = "value") //<5>
    Class<?>[] value() default {};

    @AliasFor(annotation = WebMvcTest.class, attribute = "controllers") //<6>
    Class<?>[] controllers() default {};
}
//end::class[]
