package com.spring.security_impl.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateNoteDTO(

        @NotBlank(message = "El titulo es requerido")
        String title,

        String content,

        @NotBlank(message = "El usuario es requerido")
        String user
) {
}
