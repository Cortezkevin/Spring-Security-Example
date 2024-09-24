package com.spring.security_impl.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Paso por la clase CustomAuthenticationEntrypoint {}", authException.getMessage());

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        String jsonResponse = "{\"error\": {\"message\": \"" + "Debe estar autenticado para acceder a este recurso" + "\"}}";

        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
