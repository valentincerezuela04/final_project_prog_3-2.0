package com.musicspring.app.music_app.review.songReview.model.mapper;

import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.review.songReview.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.review.songReview.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.song.model.dto.SongResponse;
import com.musicspring.app.music_app.song.model.entity.SongEntity;
import com.musicspring.app.music_app.song.repository.SongRepository;
import com.musicspring.app.music_app.user.model.entity.UserEntity;
import com.musicspring.app.music_app.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SongReviewMapper {

    public SongReviewResponse toResponse(SongReviewEntity songReview) {
        if (songReview == null){
            return null;
        }
        return SongReviewResponse.builder()
                .userId(songReview.getUser().getUserId())
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

    public Page<SongReviewResponse> toResponsePage(Page<SongReviewEntity> songReviewPage) {
        if (songReviewPage == null) {
            return Page.empty();
        }
        return songReviewPage.map(this::toResponse);
    }

    public SongReviewEntity toEntity (SongReviewRequest songReviewRequest, UserEntity userEntity,SongEntity songEntity){
        return SongReviewEntity.builder()
                .active(true)
                .description(songReviewRequest.getDescription())
                .rating(songReviewRequest.getRating())
                .song(songEntity)
                .user(userEntity)
                .build();
    }
}
