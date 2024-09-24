package com.spring.security_impl.controller;

import com.spring.security_impl.dto.CreateNoteDTO;
import com.spring.security_impl.dto.NoteDTO;
import com.spring.security_impl.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> findAll(){
        List<NoteDTO> noteDTOList = (List<NoteDTO>) noteService.findAll();
        return noteDTOList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok( noteDTOList );
    }

    @PostMapping
    public ResponseEntity<NoteDTO> create(
            @RequestBody CreateNoteDTO createNoteDTO
    ){
        NoteDTO noteDTO = noteService.create(createNoteDTO );
        return ResponseEntity
                .created(URI.create("note"))
                .body( noteDTO );
    }
}
