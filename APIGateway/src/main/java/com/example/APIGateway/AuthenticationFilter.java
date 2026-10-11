package com.example.APIGateway;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;


import java.io.IOException;

public class AuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public AuthenticationFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Only apply JWT validation to our gateway API routes.
        boolean isGatewayApi =
                path.startsWith("/Users/")
                        || path.startsWith("/Orders/");

        if (!isGatewayApi) {
            filterChain.doFilter(request, response);
            return;
        }

        // Public endpoints: registration and future login endpoint.
        if (path.equals("/Users/create")
                || path.equals("/Users/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            unauthorized(response, "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7).trim();

        if (token.isEmpty() || !jwtUtil.isTokenValid(token)) {
            unauthorized(response, "Invalid or expired JWT");
            return;
        }

        // The JWT is valid. Continue to the Gateway route.
        filterChain.doFilter(request, response);
    }

    private void unauthorized(
            HttpServletResponse response,
            String message
    ) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
                "{\"error\":\"Unauthorized\",\"message\":\""
                        + message + "\"}"
        );
    }
}
