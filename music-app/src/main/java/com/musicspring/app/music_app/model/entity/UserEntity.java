package com.musicspring.app.music_app.model.entity;

import com.musicspring.app.music_app.security.entity.CredentialEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CredentialEntity credential;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "user")
    private List<ReactionEntity> reactions;

    @OneToMany(mappedBy = "user")
    private List<ReviewEntity> reviews;
}
