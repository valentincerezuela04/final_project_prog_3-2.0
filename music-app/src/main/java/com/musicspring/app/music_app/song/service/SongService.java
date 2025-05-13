package com.musicspring.app.music_app.song.service;

import com.musicspring.app.music_app.shared.IService;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class SongService implements IService<SongEntity> {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Page<SongEntity> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    @Override
    public SongEntity findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void save(SongEntity songEntity) {

    }
}
