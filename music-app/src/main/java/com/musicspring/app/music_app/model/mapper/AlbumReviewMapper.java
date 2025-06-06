package com.musicspring.app.music_app.model.mapper;

import com.musicspring.app.music_app.model.entity.AlbumEntity;
import com.musicspring.app.music_app.model.dto.request.AlbumReviewRequest;
import com.musicspring.app.music_app.model.dto.response.AlbumReviewResponse;
import com.musicspring.app.music_app.model.entity.AlbumReviewEntity;
import com.musicspring.app.music_app.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlbumReviewMapper {

    private final UserMapper userMapper;
    private final AlbumMapper albumMapper;

    @Autowired
    public AlbumReviewMapper(UserMapper userMapper, AlbumMapper albumMapper) {
        this.userMapper = userMapper;
        this.albumMapper = albumMapper;
    }

    public AlbumReviewResponse toResponse(AlbumReviewEntity albumReview) {
        return AlbumReviewResponse.builder()
                .albumReviewId(albumReview.getReviewId())
                .rating(albumReview.getRating())
                .description(albumReview.getDescription())
                .date(albumReview.getDate())
                .active(albumReview.getActive())
                .user(userMapper.toResponse(albumReview.getUser()))
                .album(albumMapper.toResponse(albumReview.getAlbum()))
                .build();
    }

    public List<AlbumReviewResponse> toResponseList(List<AlbumReviewEntity> albumReviews) {
        return albumReviews.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<AlbumReviewResponse> toResponsePage(Page<AlbumReviewEntity> albumReviewPage) {
        return albumReviewPage.map(this::toResponse);
    }

    public AlbumReviewEntity toEntity (AlbumReviewRequest albumReviewRequest, UserEntity userEntity, AlbumEntity albumEntity){
        return AlbumReviewEntity.builder()
                .active(true)
//                .reviewId(albumReviewRequest.getAlbumId())
                .description(albumReviewRequest.getDescription())
                .rating(albumReviewRequest.getRating())
                .album(albumEntity)
                .user(userEntity)
                .build();
    }

}
