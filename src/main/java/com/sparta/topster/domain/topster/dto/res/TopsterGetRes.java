package com.sparta.topster.domain.topster.dto.res;

import com.sparta.topster.domain.album.dto.res.AlbumRes;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TopsterGetRes {
    String title;
    String content;
    List<AlbumRes> albumResList;
}
