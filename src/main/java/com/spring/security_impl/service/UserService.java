package com.spring.security_impl.service;

import com.spring.security_impl.dto.UserDTO;
import com.spring.security_impl.exception.AuthException;
import com.spring.security_impl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @SneakyThrows
    @PostAuthorize("(returnObject.username == authentication.name) || hasRole('ADMIN')")
    public UserDTO findById(Integer id){
        return userRepository.findById( id )
                .map( UserDTO::parseFrom )
                .orElseThrow(() -> new AuthException("Usuario no encontrado con id: " + id));
    }

}
