package com.spring.security_impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SecurityImplApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityImplApplication.class, args);
	}

}
