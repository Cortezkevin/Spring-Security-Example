package com.spring.security_impl.security;

import com.spring.security_impl.model.AuthUser;
import com.spring.security_impl.model.User;
import com.spring.security_impl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername( username );
        if( userOptional.isEmpty() ){
            throw new UsernameNotFoundException("Nombre de usuario: " + username + " no existente.");
        }
        return new AuthUser( userOptional.get() );
    }
}
