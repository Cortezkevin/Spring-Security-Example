package com.spring.security_impl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @GetMapping
    public ResponseEntity<String> publicEndpoint(){
        return ResponseEntity.ok("Este es un recurso publico.");
    }

}
