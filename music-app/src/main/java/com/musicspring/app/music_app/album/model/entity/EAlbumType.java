package com.musicspring.app.music_app.album.model.entity;

import lombok.Getter;

public enum EAlbumType {
    ALBUM ("Album"), SINGLE ("Single"), COMPILATION ("Compilation");

    @Getter
    private String type;

    EAlbumType(String type){this.type=type;}
}
