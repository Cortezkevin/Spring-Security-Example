package com.spring.security_impl.controller;

import com.spring.security_impl.dto.UserDTO;
import com.spring.security_impl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findByUser(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok( userService.findById( id ) );
    }
}
