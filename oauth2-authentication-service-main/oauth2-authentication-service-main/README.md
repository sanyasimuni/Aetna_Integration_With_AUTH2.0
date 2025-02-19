# OAuth2 Authentication Service with Google and GitHub

This Spring Boot application implements OAuth 2.0 authentication with JWT for secure API access. It integrates both Google and GitHub as OAuth providers, allowing users to authenticate via these platforms. The project demonstrates the configuration of security, protecting endpoints, and setting up OAuth 2.0 provider details for seamless authentication and authorization.

## Features

- **OAuth 2.0 Authentication**: Secure your API with OAuth 2.0 and JWT.
- **Google and GitHub Login**: Authenticate users through Google and GitHub OAuth 2.0 providers.
- **Secure API Endpoints**: Protect API endpoints with role-based access control.
- **Spring Boot and Spring Security**: Leverage Spring's powerful security framework.

## Getting Started

### Prerequisites

- Java 21 or above
- Maven 3.6.3 or above
- Google and GitHub Developer accounts with OAuth 2.0 credentials.

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/ishrivasayush/oauth2-authentication-service.git
    cd oauth2-authentication-service
    ```

2. **Configure OAuth2 Clients:**

    Update `application.properties` with your Google and GitHub OAuth 2.0 client details.

3. **Build and Run the Application:**

    Use Maven to build and run the application:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. **Access the Application:**

    Navigate to `http://localhost:8000` in your browser. You'll see options to authenticate via Google or GitHub.

## Usage

### Securing API Endpoints

This application includes protected API endpoints that require authentication. For example:

```java
@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "This is a private endpoint, accessible only to authenticated users.";
    }
}

###  Key Points Covered:
- **OAuth 2.0 Integration**: Instructions for setting up Google and GitHub as OAuth 2.0 providers.
- **Application Configuration**: How to update the `application.yml` file with OAuth credentials.
- **Basic API Security**: Example on how to secure API endpoints.
- **Testing the API**: Example cURL request for testing the protected endpoints.
- **Contributing and Contact Information**: How others can contribute and reach out for support.

You can customize the placeholders like `YOUR_GOOGLE_CLIENT_ID`, `YOUR_GITHUB_CLIENT_ID` with your actual details.

