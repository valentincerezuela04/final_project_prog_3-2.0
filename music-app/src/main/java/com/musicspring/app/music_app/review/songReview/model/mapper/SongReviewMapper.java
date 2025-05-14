package com.musicspring.app.music_app.review.songReview.model.mapper;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SongReviewMapper {

    private final SongRepository songRepository;
    private final UserRepository userRepository;

    @Autowired
    public SongReviewMapper(SongRepository songRepository,UserRepository userRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    public SongReviewResponse toResponse(SongReviewEntity songReview) {
        if (songReview == null){
            return null;
        }
        return SongReviewResponse.builder()
                .username(songReview.getUser().getUsername())
                .rating(songReview.getRating())
                .description(songReview.getDescription())
                .build();
    }

    public List<SongReviewResponse> toResponseList(List<SongReviewEntity> songReviews) {
        if (songReviews == null) {
            return null;
        }
        return songReviews.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    public SongReviewEntity toEntity (SongReviewRequest songReviewRequest){
        return SongReviewEntity.builder()
                .active(true)
                .description(songReviewRequest.getDescription())
                .rating(songReviewRequest.getRating())
                .song(songRepository.findById(songReviewRequest.getSongId())
                        .orElseThrow(()-> new EntityNotFoundException("Song not found")))
                .user(userRepository.findById(songReviewRequest.getUserId())
                        .orElseThrow(()->new EntityNotFoundException("User not found")))
                .build();
    }

}
