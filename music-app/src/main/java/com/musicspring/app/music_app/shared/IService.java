package com.musicspring.app.music_app.shared;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IService<T> {
    Page<T> findAll(Pageable pageable);
    T findById(Long id);
    void deleteById(Long id);
    T save (T t);
}
