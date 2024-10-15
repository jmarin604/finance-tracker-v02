package com.finance.tracker.security;

public class CustomSecurityConstants {

    private CustomSecurityConstants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String LOGIN_URL = "/api/v1/auth/login";

    public static final String ACTUATOR_URL = "/actuator/*";

    // Path http://localhost:9090/swagger-ui/index.html
    public static final String API_DOCS = "/v3/api-docs/**";
    public static final String SWAGGER_UI = "/swagger-ui/**";

    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";

    public static final String ISSUER_INFO = "https://pablito-portfolio.netlify.app";
    public static final String SUPER_SECRET_KEY = "q7r8s9t0u1v2w3x4y5z6Q7R8S9T0U1V2W3X4Y5Z6a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

}
