package com.abbos.financetrackerbot.core.handler;

import com.abbos.financetrackerbot.core.exception.BaseException;
import com.abbos.financetrackerbot.domain.dto.ErrorResponse;
import com.abbos.financetrackerbot.domain.dto.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Aliabbos Ashurov
 * @since 12/January/2025  12:38
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${project.log.mode}")
    private boolean LOG_MODE;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Response<ErrorResponse>> handleCryptoOperationException(BaseException ex, HttpServletRequest request) {
        logException(ex, request);
        var errorResponse = ErrorResponse.of(ex.getCode(), ex.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(Response.error(
                ex.getHttpStatus().value(),
                errorResponse
        ), ex.getHttpStatus());
    }

    private void logException(Exception ex, HttpServletRequest request) {
        if (LOG_MODE) {
            log.error("Exception occurred at URI: [{}] MESSAGE: {}", request.getRequestURI(), ex.getMessage());
        }
    }
}
