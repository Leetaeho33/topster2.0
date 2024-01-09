package com.sparta.topster.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListRes {

    private Long id;
    private String nickname;
    private String title;
    private String createdAt;

}
