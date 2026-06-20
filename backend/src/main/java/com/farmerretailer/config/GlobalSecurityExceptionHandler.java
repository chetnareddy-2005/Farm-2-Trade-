package com.farmerretailer.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalSecurityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        System.err.println("[Security Debug] ACCESS DENIED on " + request.getRequestURI() + " | Reason: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(java.util.Map.of(
            "error", "Access Denied",
            "message", ex.getMessage(),
            "path", request.getRequestURI()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, HttpServletRequest request) {
        System.err.println("[Security Debug] INTERNAL ERROR on " + request.getRequestURI() + " | Type: " + ex.getClass().getName() + " | Msg: " + ex.getMessage());
        // Do not return 403 for general exceptions, but log them
        return null; 
    }
}
