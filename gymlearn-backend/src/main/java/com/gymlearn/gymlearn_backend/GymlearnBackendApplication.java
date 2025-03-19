package com.gymlearn.gymlearn_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.gymlearn.gymlearn_backend.security.JwtConfig;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class GymlearnBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymlearnBackendApplication.class, args);
	}

}