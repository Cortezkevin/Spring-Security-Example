package com.spring.security_impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping
    public ResponseEntity<String> adminEndpoint(){
        return ResponseEntity.ok( "Esta es una endpoint segura para administradores." );
    }

}
