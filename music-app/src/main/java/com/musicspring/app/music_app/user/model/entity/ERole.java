package com.musicspring.app.music_app.user.model.entity;

import lombok.Getter;

public enum ERole {
    ADMIN ("Admin"), USER ("User");

    @Getter
    private String role;

    ERole(String role) {
        this.role = role;
    }
}