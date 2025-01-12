package com.abbos.financetrackerbot.core.config.security;

import com.abbos.financetrackerbot.domain.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  13:10
 **/
@Configuration
@RequiredArgsConstructor
public class SecurityBeansConfiguration {

    private final ObjectMapper objectMapper;

    private static final int ERROR_CODE_ACCESS_DENIED = 403;
    private static final int ERROR_CODE_UNAUTHORIZED = 401;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> handleError(response, accessDeniedException, ERROR_CODE_ACCESS_DENIED, request.getRequestURI());
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> handleError(response, authException, ERROR_CODE_UNAUTHORIZED, request.getRequestURI());
    }

    @SneakyThrows
    private void handleError(HttpServletResponse response, Exception exception, int errorCode, String errorPath) {
        var errorMessage = exception.getMessage();
        System.err.println(errorMessage);
        var errorResponse = ErrorResponse.of(String.valueOf(errorCode), errorMessage, errorPath);
        response.setStatus(errorCode);
        response.setContentType("application/json");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            objectMapper.writeValue(outputStream, errorResponse);
        }
    }
}
