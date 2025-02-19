package com.tier3hub.oauth2_authentication_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Oauth2AuthenticationServiceApplication {

	//Only Added Spring Boot Web Dependency
	public static void main(String[] args) {
		SpringApplication.run(Oauth2AuthenticationServiceApplication.class, args);
	}

}
