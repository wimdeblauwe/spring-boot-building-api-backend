package com.springbook.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// tag::class[]
@SpringBootApplication //<1>
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
// end::class[]
