package com.example.copsboot.user;

import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    //tag::testStoreUser[]
    @Test
    public void testStoreUser() {
        User user = repository.save(new User(repository.nextId(),
                "alex.foley@beverly-hills.com",
                new AuthServerId(UUID.randomUUID()),
                "c41536a5a8b9d3f14a7e5472a5322b5e1f76a6e7a9255c2c2e7e0d3a2c5b9d0"));
        assertThat(user).isNotNull();

        assertThat(repository.count()).isEqualTo(1L);
    }
    //end::testStoreUser[]

    //tag::testconfig[]
    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator<UUID> generator() {
            return new InMemoryUniqueIdGenerator();
        }
    }
    //end::testconfig[]
}
