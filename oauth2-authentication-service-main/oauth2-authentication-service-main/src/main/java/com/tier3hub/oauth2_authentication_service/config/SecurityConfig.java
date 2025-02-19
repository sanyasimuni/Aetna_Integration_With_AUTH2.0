//package com.tier3hub.oauth2_authentication_service.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	
//	@Bean
//	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    http.csrf(c->c.disable())
//	        .authorizeHttpRequests(authorize -> authorize
//	            .requestMatchers("/", "/login*", "/error*","/oauth2/authorization/aetna").permitAll()  // Public endpoints
//	            .anyRequest().authenticated()  // All other endpoints require authentication
//	        )
//	        .oauth2Login(oauth2 -> oauth2
//	            .defaultSuccessUrl("/authorize", true)  // Redirect after successful login
//	        );
//	    return http.build();
//	}
//}
