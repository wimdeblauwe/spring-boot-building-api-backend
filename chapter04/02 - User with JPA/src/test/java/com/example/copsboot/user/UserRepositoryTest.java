package com.example.copsboot.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) //<1>
@DataJpaTest //<2>
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository; //<3>

    @Test
    public void testStoreUser() { //<4>
        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(new User(UUID.randomUUID(), //<5>
                                             "alex.foley@beverly-hills.com",
                                             "my-secret-pwd",
                                             roles));
        assertThat(user).isNotNull(); //<6>

        assertThat(repository.count()).isEqualTo(1L); //<7>
    }
}