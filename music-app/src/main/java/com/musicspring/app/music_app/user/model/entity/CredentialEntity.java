package com.musicspring.app.music_app.user.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "credentials")

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CredentialEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @NotBlank
    @Size(min = 8, max = 120)
    @Column(name = "password", nullable = false)
    private String password;
}
