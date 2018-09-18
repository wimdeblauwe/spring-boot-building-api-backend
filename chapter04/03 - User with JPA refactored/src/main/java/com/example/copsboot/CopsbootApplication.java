package com.example.copsboot;

import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class CopsbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopsbootApplication.class, args);
	}

	//tag::unique-id-generator[]
	@Bean
	public UniqueIdGenerator<UUID> uniqueIdGenerator() {
		return new InMemoryUniqueIdGenerator();
	}
	//end::unique-id-generator[]
}
