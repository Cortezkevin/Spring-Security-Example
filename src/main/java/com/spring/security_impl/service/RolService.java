package com.spring.security_impl.service;

import com.spring.security_impl.enums.RolName;
import com.spring.security_impl.model.Rol;
import com.spring.security_impl.repository.RolRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    @PostConstruct
    public void initRoles(){
        Rol rolAdmin = Rol.builder().rolName(RolName.ROLE_ADMIN).build();
        Rol rolUser = Rol.builder().rolName(RolName.ROLE_USER).build();
        List<Rol> rolList = List.of( rolAdmin, rolUser );
        rolList.forEach( r -> {
            Optional<Rol> rolDB = rolRepository.findByRolName( r.getRolName() );
            if(rolDB.isEmpty()){
                rolRepository.save( r );
            }
        });
    }

}
