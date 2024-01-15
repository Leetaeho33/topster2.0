package com.sparta.topster.domain.topster.dto.res;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TopsterGetNotLoginRes {
    Long id;
    String title;
    String author;
    String content;
    Long likeCount;
    List<AlbumRes> albums;
    LocalDateTime createdAt;
}
