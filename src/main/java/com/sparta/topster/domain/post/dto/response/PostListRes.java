package com.sparta.topster.domain.post.dto.response;

import lombok.Getter;

@Getter
public class PostListRes {

    private Long id;
    private String nickname;
    private String title;
    private String createdAt;

}
