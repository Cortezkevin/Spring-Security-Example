package com.spring.security_impl.dto;

public record AuthResponseDTO(
        String username,
        String token
) {
}
