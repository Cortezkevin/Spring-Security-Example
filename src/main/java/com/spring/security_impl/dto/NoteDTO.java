package com.spring.security_impl.dto;

import com.spring.security_impl.model.Note;

import java.time.Instant;

public record NoteDTO(
        String title,
        String content,
        String user,
        String createdBy,
        Instant createdAt
) {
    public static NoteDTO parseFrom(Note note){
        return new NoteDTO(
                note.getTitle(),
                note.getContent(),
                note.getUser().getUsername(),
                note.getCreatedBy(),
                note.getCreatedAt()
        );
    }
}
