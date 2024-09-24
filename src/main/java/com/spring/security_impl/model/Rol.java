package com.spring.security_impl.model;

import com.spring.security_impl.enums.RolName;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Enumerated( EnumType.STRING )
    private RolName rolName;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();
}
