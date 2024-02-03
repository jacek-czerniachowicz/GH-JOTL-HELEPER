package com.gloomhaven.helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class HelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelperApplication.class, args);
	}
}
