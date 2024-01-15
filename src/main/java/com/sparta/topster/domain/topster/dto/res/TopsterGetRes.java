package com.sparta.topster.domain.topster.dto.res;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TopsterGetRes {
    Long id;
    String title;
    String author;
    String content;
    Long likeCount;
    Boolean likeStatus;
    List<AlbumRes> albums;
    LocalDateTime createdAt;
}
