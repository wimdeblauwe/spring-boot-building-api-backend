package com.example.copsboot.user;

import com.example.copsboot.infrastructure.SpringProfiles;
import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //<1>
@ActiveProfiles(SpringProfiles.REPOSITORY_TEST) //<2>
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testStoreUser() {
        User user = repository.save(new User(repository.nextId(),
                "alex.foley@beverly-hills.com",
                new AuthServerId(UUID.randomUUID()),
                "c41536a5a8b9d3f14a7e5472a5322b5e1f76a6e7a9255c2c2e7e0d3a2c5b9d0"));
        assertThat(user).isNotNull();

        assertThat(repository.count()).isEqualTo(1L);

        entityManager.flush(); //<3>

        assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM copsboot_user", Long.class)).isEqualTo(1L); //<4>
        assertThat(jdbcTemplate.queryForObject("SELECT email FROM copsboot_user", String.class)).isEqualTo("alex.foley@beverly-hills.com");
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator<UUID> generator() {
            return new InMemoryUniqueIdGenerator();
        }
    }
}
