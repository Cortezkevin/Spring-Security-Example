package com.spring.security_impl.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("Paso por la clase CustomAccessDeniedHandler {}", accessDeniedException.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        String jsonResponse = "{\"error\": {\"message\": \"" + "No tiene los permisos o roles necesarios para acceder a este recurso" + "\"}}";

        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }

}
