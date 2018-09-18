package com.example.copsboot.user;

import com.example.copsboot.infrastructure.SpringProfiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //<1>
@ActiveProfiles(SpringProfiles.INTEGRATION_TEST) //<2>
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository repository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testSaveUser() {
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(new User(repository.nextId(),
                                             "alex.foley@beverly-hills.com",
                                             "my-secret-pwd",
                                             roles));
        assertThat(user).isNotNull();

        assertThat(repository.count()).isEqualTo(1L);

        entityManager.flush(); //<3>
        assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM copsboot_user", Long.class)).isEqualTo(1L); //<4>
        assertThat(jdbcTemplate.queryForObject("SELECT count(*) FROM user_roles", Long.class)).isEqualTo(1L);
        assertThat(jdbcTemplate.queryForObject("SELECT roles FROM user_roles", String.class)).isEqualTo("OFFICER");
    }
}
