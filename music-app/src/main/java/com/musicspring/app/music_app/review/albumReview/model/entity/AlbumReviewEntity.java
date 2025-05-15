package com.musicspring.app.music_app.review.albumReview.model.entity;

import com.musicspring.app.music_app.album.model.entity.AlbumEntity;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "album_reviews")
@Check(constraints = "rating >= 0.5 AND rating <= 5.0")
/// Constraint para el rating

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AlbumReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_review_id")
    private Long albumReviewId;

    @Column(name = "rating", nullable = true, precision = 3, scale = 2, columnDefinition = "DECIMAL(1,2) DEFAULT 5.0")
    /// scale indica el numero de decimales
    /// precision indica que el maximo de digitos en total es 3
    /// Si no se agrega un rating, el default es 5.0h
    private Double rating;

    @Column(name = "description",length = 500)
    private String description;

    @CreationTimestamp
    /// Automaticamente asigna fecha y hora actuales cuando se guarda la entidad en la db.Precaucion, si no guardo la entidad, el valor del campo estara vacio
    @Column(nullable = false, updatable = false)
    /// Me aseguro que el campo no se pueda actualizar una vez creado
    private LocalDateTime date;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private AlbumEntity album;

    /// Usuario que realiza la reseña
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}