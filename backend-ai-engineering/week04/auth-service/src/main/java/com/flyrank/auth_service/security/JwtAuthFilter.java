package com.flyrank.auth_service.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flyrank.auth_service.dto.ErrorResponse;
import com.flyrank.auth_service.service.SupabaseAuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Reusable JWT Authentication filter verifying bearer tokens against Supabase.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final SupabaseAuthService authService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthFilter(SupabaseAuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/public/") || path.startsWith("/docs") || path.startsWith("/v3/api-docs") || path.equals("/auth/signup") || path.equals("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Access token required");
            return;
        }

        String token = authHeader.substring(7).trim();
        if (token.isEmpty()) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Access token required");
            return;
        }

        JsonNode userJson = authService.verifyTokenAndGetUser(token);
        if (userJson == null) {
            sendErrorResponse(response, HttpStatus.UNAUTHORIZED, "Invalid or expired token");
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userJson, token, Collections.emptyList());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(message);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
