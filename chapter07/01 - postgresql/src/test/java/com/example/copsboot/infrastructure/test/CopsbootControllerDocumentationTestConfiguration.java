package com.example.copsboot.infrastructure.test;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@TestConfiguration
class CopsbootControllerDocumentationTestConfiguration {
        @Bean
        public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
            return configurer -> configurer.operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint(),
                            modifyHeaders().removeMatching("X.*")
                                    .removeMatching("Pragma")
                                    .removeMatching("Expires"));
        }
    }
