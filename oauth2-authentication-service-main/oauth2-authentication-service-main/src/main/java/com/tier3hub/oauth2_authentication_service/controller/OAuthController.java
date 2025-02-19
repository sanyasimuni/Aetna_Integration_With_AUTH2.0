package com.tier3hub.oauth2_authentication_service.controller;


import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class OAuthController {

	@GetMapping("/authorize")
	public void authorize(HttpServletResponse response) throws IOException {
	    String authorizationUrl = "https://vteapif1.aetna.com/fhirdemo/v1/fhirserver_auth/oauth2/authorize"
	            + "?response_type=code" 
	    		+ "&client_id=675de5ddbcf0953cffd3a7e4830faa34"
	            + "&redirect_uri=http://your-application.com/registered/callback" 
	    		+ "&scope=launch/patient%20patient/*.read"
	            + "&state=1234" 
	    		+"&aud=https://vteapif1.aetna.com/fhirdemo";
	    response.sendRedirect(authorizationUrl);
	}


	private String clientId = "675de5ddbcf0953cffd3a7e4830faa34";

	private String clientSecret = "559ecf755c2cc6c4794f6da0e4957c8f";

	@GetMapping("/registered/callback")
	@ResponseBody
	public String handleCallback(@RequestParam("code") String authorizationCode) {
		String tokenUrl = "https://vteapif1.aetna.com/fhirdemo/v1/fhirserver_auth/oauth2/token";

		// Prepare the request body for token exchange
		String requestBody = "grant_type=authorization_code" 
		+ "&code=" + authorizationCode 
		+ "&client_id=" + clientId
		+ "&client_secret="
		+ clientSecret;

		// Set headers for token exchange
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// Create the request entity for token exchange
		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		// Create RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Send POST request to exchange authorization code for access token
		ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);

		// Handle the response from token exchange
		if (response.getStatusCode() == HttpStatus.OK) {
			// Parse the response to extract the access token
			String responseBody = response.getBody();
			String accessToken = extractAccessToken(responseBody);

			// Use the access token to make a request to the Patient Access API
			String patientApiUrl = "https://vteapif1.aetna.com/fhirdemo/v2/patientaccess/Patient";
			HttpHeaders patientApiHeaders = new HttpHeaders();
			patientApiHeaders.set("Authorization", "Bearer " + accessToken);
			HttpEntity<String> patientApiEntity = new HttpEntity<>(patientApiHeaders);

			// Send GET request to Patient Access API
			ResponseEntity<String> patientApiResponse = restTemplate.exchange(patientApiUrl, HttpMethod.GET,
					patientApiEntity, String.class);

			// Return the response from the Patient Access API
			if (patientApiResponse.getStatusCode() == HttpStatus.OK) {
				return patientApiResponse.getBody();
			} else {
				return "Error: " + patientApiResponse.getStatusCode() + " - " + patientApiResponse.getBody();
			}
		} else {
			return "Error: " + response.getStatusCode() + " - " + response.getBody();
		}
	}

	// Helper method to extract access token from the response body
	private String extractAccessToken(String responseBody) {
	    try {
	        // Log the response body for debugging
	        System.out.println("Response Body: " + responseBody);

	        // Check if the response body is in JSON format
	        if (responseBody.trim().startsWith("{") && responseBody.trim().endsWith("}")) {
	            // Create ObjectMapper instance
	            ObjectMapper objectMapper = new ObjectMapper();

	            // Parse the response body into a JsonNode
	            JsonNode jsonNode = objectMapper.readTree(responseBody);

	            // Extract the access_token field
	            JsonNode accessTokenNode = jsonNode.get("access_token");

	            // Return the access token as a string
	            return accessTokenNode != null ? accessTokenNode.asText() : null;
	        } else {
	            // Handle non-JSON response
	            System.err.println("Response is not in JSON format.");
	            return null;
	        }
	    } catch (Exception e) {
	        // Handle parsing exceptions
	        e.printStackTrace();
	       // return null;
	    }
		return responseBody;
	}

}
