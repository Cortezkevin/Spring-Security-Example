package com.spring.security_impl.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notes")
@EntityListeners(AuditingEntityListener.class)
public class Note {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Instant createdAt;
}
