package com.spring.security_impl.repository;

import com.spring.security_impl.enums.RolName;
import com.spring.security_impl.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolName(RolName rolName);
}
