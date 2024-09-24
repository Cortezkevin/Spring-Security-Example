package com.spring.security_impl.service;

import com.spring.security_impl.dto.AuthResponseDTO;
import com.spring.security_impl.dto.CreateUserDTO;
import com.spring.security_impl.dto.LoginDTO;
import com.spring.security_impl.enums.RolName;
import com.spring.security_impl.exception.AuthException;
import com.spring.security_impl.model.Rol;
import com.spring.security_impl.model.User;
import com.spring.security_impl.repository.RolRepository;
import com.spring.security_impl.repository.UserRepository;
import com.spring.security_impl.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class AuthService {

    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @SneakyThrows
    public AuthResponseDTO create(@Valid CreateUserDTO createUserDTO){
        Optional<User> userOptional = userRepository.findByUsername( createUserDTO.username() );
        if( userOptional.isPresent() ){
            throw new AuthException("Ese nombre de usuario ya esta en uso");
        }

        Rol rolUser = rolRepository.findByRolName( RolName.ROLE_USER ).get();
        List<Rol> rolList = new ArrayList<>( List.of( rolUser ) );

        if( createUserDTO.isAdmin() ){
            Rol rolAdmin = rolRepository.findByRolName( RolName.ROLE_ADMIN ).get();
            rolList.add( rolAdmin );
        }

        User userToCreate = User.builder()
                .username( createUserDTO.username() )
                .password( passwordEncoder.encode( createUserDTO.password() ) )
                .roles( rolList )
                .build();

        User userCreated = userRepository.save( userToCreate );

        return new AuthResponseDTO(
                userCreated.getUsername(),
                jwtUtils.generateToken( userCreated )
        );
    }

    @SneakyThrows
    public AuthResponseDTO login(@Valid LoginDTO loginDTO){
        Optional<User> userOptional = userRepository.findByUsername( loginDTO.username() );
        if( userOptional.isEmpty() ){
            throw new AuthException("Ese nombre de usuario no existe");
        }

        User user = userOptional.get();
        boolean isPasswordValid = passwordEncoder.matches( loginDTO.password(), user.getPassword() );

        if(!isPasswordValid){
            throw new AuthException("La contraseña es invalida");
        }

        return new AuthResponseDTO(
                user.getUsername(),
                jwtUtils.generateToken( user )
        );
    }

    @SneakyThrows
    public AuthResponseDTO verifyToken(String token) {
        String username = jwtUtils.getUsernameFromToken( token );
        Optional<User> userOptional  = userRepository.findByUsername( username );
        if( userOptional.isEmpty() ){
            throw new AuthException("Usuario no encontrado");
        }

        return new AuthResponseDTO(
                username,
                jwtUtils.generateToken( userOptional.get() )
        );
    }
}
