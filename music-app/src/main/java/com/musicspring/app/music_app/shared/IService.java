package com.musicspring.app.music_app.shared;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IService<T> {
    public Page<T> findAll();
    public Optional<T> findById(Long id);
    public void deleteById(Long id);
    public void save (T t);
}
