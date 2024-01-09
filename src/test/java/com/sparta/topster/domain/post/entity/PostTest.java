package com.sparta.topster.domain.post.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void update_메소드를_통해_제목과_내용을_수정할_수_있다() {
        // given
        Post post = Post.builder()
            .title("제목")
            .content("내용")
            .build();

        // when
        post.update("제목 수정", "내용 수정");

        // then
        assertThat(post.getTitle()).isEqualTo("제목 수정");
        assertThat(post.getContent()).isEqualTo("내용 수정");
    }

}