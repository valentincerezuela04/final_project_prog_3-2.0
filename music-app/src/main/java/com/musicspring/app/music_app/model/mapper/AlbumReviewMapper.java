package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.entity.AlbumEntity;
import com.musicspring.app.music_app.model.dto.AlbumReviewRequest;
import com.musicspring.app.music_app.model.dto.AlbumReviewResponse;
import com.musicspring.app.music_app.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumReviewMapper {

    public AlbumReviewResponse toResponse(AlbumReviewEntity albumReview) {
        if (albumReview == null){
            return null;
        }
        return AlbumReviewResponse.builder()
                .userId(albumReview.getUser().getUserId())
                .username(albumReview.getUser().getUsername())
                .rating(albumReview.getRating())
                .description(albumReview.getDescription())
                .build();
    }

    public List<AlbumReviewResponse> toResponseList(List<AlbumReviewEntity> albumReviews) {
        if (albumReviews == null) {
            return null;
        }
        return albumReviews.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<AlbumReviewResponse> toResponsePage(Page<AlbumReviewEntity> albumReviewPage) {
        if (albumReviewPage == null) {
            return Page.empty();
        }
        return albumReviewPage.map(this::toResponse);
    }

    public AlbumReviewEntity toEntity (AlbumReviewRequest albumReviewRequest, UserEntity userEntity, AlbumEntity albumEntity){
        return AlbumReviewEntity.builder()
                .active(true)
                .reviewId(albumReviewRequest.getAlbumId())
                .description(albumReviewRequest.getDescription())
                .rating(albumReviewRequest.getRating())
                .album(albumEntity)
                .user(userEntity)
                .build();
    }

}
