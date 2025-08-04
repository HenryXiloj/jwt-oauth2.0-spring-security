# Microservices Security Patterns and Protocols

A comprehensive guide to implementing security patterns in microservices architecture using Spring Security, covering OAuth 2.0, JWT, OpenID Connect, and SAML 2.0.

📘 Blog Post: [Microservices Security Patterns and Protocols](https://jarmx.blogspot.com/2022/10/microservices-security-patterns-and.html)

## Overview

This repository demonstrates the implementation of various security protocols for microservices:

- **OAuth 2.0 Authorization Framework** with Spring Boot 2.5.2 and Java 11
- **JSON Web Tokens (JWT)** with Spring Boot 2.4.4 and Kotlin 1.7.10
- **OpenID Connect** implementation
- **SAML V2.0** integration

## Security Protocols

### 1. OAuth 2.0 Authorization Framework

OAuth 2.0 is the industry-standard protocol for authorization, focusing on client developer simplicity while providing specific authorization flows for various application types.

#### OAuth 2.0 Roles

- **Resource Owner**: The user or system that owns the data to be shared
- **Resource Server**: Server that protects user resources (e.g., Facebook, Google)
- **Client Application**: Application requiring access to protected resources
- **Authorization Server**: Server authorizing the client app to access resources

#### Features
- Integration with Spring Boot and LDAP
- Authorization Server and Resource Server configuration
- Client Application setup
- LDAP authentication support

#### Technology Stack
- Spring Boot 2.5.2
- OAuth2
- Java 11
- Maven
- LDAP integration

### 2. JSON Web Tokens (JWT)

JWTs provide a compact, URL-safe means of representing claims between parties. They can be encrypted for secrecy and signed to verify integrity.

#### JWT Structure
- **Header**: Contains metadata about the token
- **Payload**: Contains the claims/data
- **Signature**: Ensures token integrity

#### Features
- OAuth2 with JWT implementation
- Spring Boot and Kotlin integration
- LDAP authentication
- Token-based security

#### Technology Stack
- Spring Boot 2.4.4
- Kotlin 1.7.10
- OAuth2
- JWT
- Java 11
- Maven

### 3. OpenID Connect

OpenID Connect is a simple identity layer on top of OAuth 2.0, allowing clients to verify end-user identity and obtain basic profile information.

#### Key Benefits
- Identity verification
- Profile information access
- REST-like implementation
- Extensible specification
- Integrated OAuth 2.0 capabilities

### 4. SAML V2.0

Security Assertion Markup Language (SAML) is an XML-based open standard for transferring identity data between identity providers (IdP) and service providers (SP).

## Project Structure

### OAuth 2.0 Implementation

```
auth/
├── src/main/java/com/henry/
│   ├── configuration/
│   │   ├── AuthServerConfig.java
│   │   └── SecurityConfig.java
│   └── controller/
│       └── HelloResource.java
├── src/main/resources/
│   ├── application.yml
│   └── test-server.ldif
└── pom.xml
```

### JWT Implementation

```
SpringBoot2-oauth2-Ldap/
├── src/main/kotlin/com/hxiloj/
│   ├── configuration/
│   │   ├── AuthorizationServerConfig.kt
│   │   ├── ResourceServerConfig.kt
│   │   └── SpringSecurityConfig.kt
│   └── controller/
│       └── DefaultRestController.kt
├── src/main/resources/
│   ├── application.yml
│   └── test-server.ldif
└── pom.xml
```

## Configuration Examples

### OAuth 2.0 Authorization Server Configuration

```java
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Value("${client.id}")
    private String clientId;
    
    @Value("${client.secret}")
    private String clientSecret;
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(clientId)
            .secret(passwordEncoder.encode(clientSecret))
            .authorizedGrantTypes("authorization_code")
            .scopes("user_info")
            .autoApprove(true)
            .redirectUris(uriRedirect);
    }
}
```

### JWT Token Configuration

```kotlin
@Bean
open fun tokenStore(): JwtTokenStore {
    return JwtTokenStore(accessTokenConverter())
}

@Bean
open fun accessTokenConverter(): JwtAccessTokenConverter {
    val converter = JwtAccessTokenConverter()
    converter.setSigningKey("123")
    return converter
}
```

## LDAP Integration

All implementations use LDAP for user authentication with the following configuration:

```yaml
spring:
  ldap:
    embedded:
      base-dn: dc=springframework,dc=org
      ldif: classpath:test-server.ldif
      port: 8389
    url: ldap://localhost:8389/
```

### Test Credentials
- **Username**: henry
- **Password**: 123

## API Endpoints

### OAuth 2.0 Endpoints
- Authorization: `GET /oauth/authorize`
- Token: `POST /oauth/token`
- Protected Resource: `GET /rest/hello`

### JWT Endpoints
- Token: `POST /oauth/token`
- User Principal: `GET /principal`
- Protected Resource: `GET /`

## Running the Applications

### Prerequisites
- Java 11
- Maven
- LDAP server (embedded)

### OAuth 2.0 Application
1. Start Authorization Server: `http://localhost:8081/auth`
2. Start Client Application: `http://localhost:8082/ui`
3. Access the application and follow OAuth flow

### JWT Application
1. Start the application: `http://localhost:9000/auth`
2. Use POSTMAN to get access token
3. Access protected resources with token

## Testing

### OAuth 2.0 Flow Testing
```bash
# Get authorization code
GET http://localhost:8082/ui

# Exchange code for token
curl --request POST \
  --url 'http://localhost:8081/auth/oauth/token' \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data grant_type=authorization_code \
  --data 'client_id=henry' \
  --data client_secret=secret \
  --data code=YOUR_AUTHORIZATION_CODE \
  --data 'redirect_uri=http://localhost:8082/ui/index'

# Access protected resource
GET http://localhost:8081/auth/rest/hello?access_token=your_token
```

### JWT Token Testing
```bash
# Get JWT token
POST http://localhost:9000/oauth/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&username=henry&password=123&client_id=henry&client_secret=secret
```

## Security Best Practices

1. **Token Management**
   - Use secure token storage
   - Implement token refresh mechanisms
   - Set appropriate token expiration times

2. **HTTPS Usage**
   - Always use HTTPS in production
   - Secure token transmission
   - Protect sensitive endpoints

3. **CORS Configuration**
   - Configure appropriate CORS policies
   - Restrict origins in production
   - Handle preflight requests

4. **LDAP Security**
   - Use secure LDAP connections
   - Implement proper authentication
   - Configure access controls

## Source Code Repositories

- **OAuth 2.0 Implementation**: [GitHub Repository](https://github.com/HenryXiloj/oauth2.0-spring-security)
- **JWT Implementation**: [GitHub Repository](https://github.com/HenryXiloj/jwt-oauth2.0-spring-security)
- **SAML 2.0 Implementation**: [GitHub Repository](https://github.com/HenryXiloj/spring-boot-security-saml2.0-keycloak)

## References

- [OAuth 2.0 Specification](https://oauth.net/2/)
- [JWT Introduction](https://jwt.io/introduction)
- [OpenID Connect](https://openid.net/connect/)
- [SAML Specifications](http://saml.xml.org/saml-specifications)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [OAuth 2.0 Roles](https://jenkov.com/tutorials/oauth2/roles.html)



## Support

For questions and support, please open an issue in the respective GitHub repository or refer to the Spring Security documentation.
