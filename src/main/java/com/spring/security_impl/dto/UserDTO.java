package com.spring.security_impl.dto;

import com.spring.security_impl.model.User;

import java.util.List;

public record UserDTO(
        String username,
        List<String> roles
) {
    public static UserDTO parseFrom(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getRoles().stream().map( r -> r.getRolName().name() ).toList()
        );
    }
}
