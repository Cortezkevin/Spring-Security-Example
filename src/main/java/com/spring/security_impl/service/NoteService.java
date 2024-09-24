package com.spring.security_impl.service;

import com.spring.security_impl.dto.CreateNoteDTO;
import com.spring.security_impl.dto.NoteDTO;
import com.spring.security_impl.exception.AuthException;
import com.spring.security_impl.model.Note;
import com.spring.security_impl.model.User;
import com.spring.security_impl.repository.NoteRepository;
import com.spring.security_impl.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @SneakyThrows
    @PreAuthorize("(#c.user == authentication.name) || hasRole('ADMIN')")
    public NoteDTO create(@P("c") @Valid CreateNoteDTO createNoteDTO){
        User user = userRepository.findByUsername( createNoteDTO.user() )
                .orElseThrow(() -> new AuthException("Usuario no encontrado"));

        Note noteToCreate = Note.builder()
                .title( createNoteDTO.title() )
                .content( createNoteDTO.content() )
                .user( user )
                .build();

        Note noteCreated = noteRepository.save( noteToCreate );
        return NoteDTO.parseFrom( noteCreated );
    }

    @PostFilter("(filterObject.user == authentication.name) || hasRole('ADMIN')")
    public Collection<NoteDTO> findAll(){
        return noteRepository.findAll().stream().map(NoteDTO::parseFrom).collect(Collectors.toList());
    }
}
