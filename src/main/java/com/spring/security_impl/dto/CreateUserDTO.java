package com.spring.security_impl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(

        @NotBlank(message = "El nombre de usuario es requerido")
        @Size(min = 3, max = 15, message = "El nombre de usuario debe contener 3 caracteres como mínimo y 15 como máximo")
        String username,

        @NotBlank(message = "La contraseña es requerida")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{4,}$", message = "La contraseña no tiene un formato valido")
        String password,

        boolean isAdmin
) {
}
