package com.spring.security_impl.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Rol> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Note> notes = new ArrayList<>();
}
