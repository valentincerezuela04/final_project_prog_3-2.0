package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.dto.SongReviewRequest;
import com.musicspring.app.music_app.model.dto.SongReviewResponse;
import com.musicspring.app.music_app.model.entity.SongReviewEntity;
import com.musicspring.app.music_app.model.entity.SongEntity;
import com.musicspring.app.music_app.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SongReviewMapper {

    private final UserMapper userMapper;
    private final SongMapper songMapper;

    @Autowired
    public SongReviewMapper(UserMapper userMapper, SongMapper songMapper) {
        this.userMapper = userMapper;
        this.songMapper = songMapper;
    }

    public SongReviewResponse toResponse(SongReviewEntity songReview) {
        if (songReview == null) {
            return null;
        }
        return SongReviewResponse.builder()
                .songReviewId(songReview.getReviewId())
                .rating(songReview.getRating())
                .description(songReview.getDescription())
                .date(songReview.getDate())
                .active(songReview.getActive())
                .user(userMapper.toResponse(songReview.getUser()))
                .song(songMapper.toResponse(songReview.getSong()))
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

    public SongReviewEntity toEntity(SongReviewRequest songReviewRequest, UserEntity userEntity, SongEntity songEntity) {
        return SongReviewEntity.builder()
                .active(true)
                .description(songReviewRequest.getDescription())
                .rating(songReviewRequest.getRating())
                .song(songEntity)
                .user(userEntity)
                .build();
    }
}
