package com.example.copsboot;

import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CopsbootApplicationConfiguration {

  @Bean
  public UniqueIdGenerator<UUID> uniqueIdGenerator() {
    return new InMemoryUniqueIdGenerator();
  }
}
