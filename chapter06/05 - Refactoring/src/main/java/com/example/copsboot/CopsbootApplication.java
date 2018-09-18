package com.example.copsboot;

import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.UUID;

@SpringBootApplication
public class CopsbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(CopsbootApplication.class, args);
    }

    @Bean
    public UniqueIdGenerator<UUID> uniqueIdGenerator() {
        return new InMemoryUniqueIdGenerator();
    }

    //tag::supporting-beans[]
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
    //end::supporting-beans[]
}
