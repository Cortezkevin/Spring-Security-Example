package com.spring.security_impl.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthException.class)
    public Map<String, Object> handleAuthException(AuthException authException){
        return Map.of("message", Map.of("error", authException.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SignatureException.class)
    public Map<String, Object> handleSignatureException(SignatureException signatureException){
        return Map.of("message", Map.of("error", signatureException.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpiredJwtException.class)
    public Map<String, Object> handleExpiredJwtException(ExpiredJwtException expiredJwtException){
        return Map.of("message", Map.of("error", expiredJwtException.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedJwtException.class)
    public Map<String, Object> handleMalformedJwtException(MalformedJwtException malformedJwtException){
        return Map.of("message", Map.of("error", malformedJwtException.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException constraintViolationException){
        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
        Map<String, Object> fieldErrors = new HashMap<>();
        constraintViolations.forEach( c -> {
            log.info("Field -> {}", c.toString());
            String[] tokens = c.getPropertyPath().toString().split("\\.");
            String fieldName = tokens[ tokens.length - 1];
            fieldErrors.put(fieldName, c.getMessage() );
        });
        return Map.of("message", Map.of("error", "Ocurrió un error al validar los campos" ), "fields", fieldErrors);
    }
}
