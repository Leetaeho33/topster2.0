package com.sparta.topster.domain.post.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListRes {

    private Long id;
    private String author;
    private String title;
    private LocalDateTime createdAt;
}
