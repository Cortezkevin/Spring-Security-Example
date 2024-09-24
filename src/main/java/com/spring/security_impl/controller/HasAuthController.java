package com.spring.security_impl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
@RequiredArgsConstructor
public class HasAuthController {

    @GetMapping
    public ResponseEntity<String> secure(){
        return ResponseEntity.ok( "Esta es una endpoint segura para cualquier usuario." );
    }

}
