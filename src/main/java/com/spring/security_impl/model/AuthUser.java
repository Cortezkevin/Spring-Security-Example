package com.spring.security_impl.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthUser implements UserDetails {

    private String password;
    private String username;
    private List<? extends  GrantedAuthority> authorities = new ArrayList<>();

    public AuthUser( User user ){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getRoles().stream().map(Rol::getRolName).map(rolName -> new SimpleGrantedAuthority(rolName.name())).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
