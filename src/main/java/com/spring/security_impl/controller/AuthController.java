package com.spring.security_impl.controller;

import com.spring.security_impl.dto.AuthResponseDTO;
import com.spring.security_impl.dto.CreateUserDTO;
import com.spring.security_impl.dto.LoginDTO;
import com.spring.security_impl.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody LoginDTO loginDTO
            ){
        return ResponseEntity.ok( authService.login( loginDTO ) );
    }

    @PostMapping("/create")
    public ResponseEntity<AuthResponseDTO> create(
            @RequestBody CreateUserDTO createUserDTO
    ){
        return ResponseEntity.ok( authService.create( createUserDTO ) );
    }

    @GetMapping("/verify")
    public ResponseEntity<AuthResponseDTO> verifyToken(
            @RequestParam(name = "token") String token
    ){
        return ResponseEntity.ok( authService.verifyToken( token ) );
    }
}
