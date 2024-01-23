package com.sparta.topster.domain.post.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.topster.domain.post.dto.request.PostPageReq;
import com.sparta.topster.domain.post.dto.request.PostSearchCond;
import com.sparta.topster.domain.post.dto.request.PostSortReq;
import com.sparta.topster.domain.post.dto.response.PostListRes;
import com.sparta.topster.domain.post.entity.Post;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.repository.TopsterRepository;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.domain.user.entity.UserRoleEnum;
import com.sparta.topster.domain.user.repository.UserRepository;
import com.sparta.topster.global.config.JpaConfig;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaConfig.class)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TopsterRepository topsterRepository;

    User user;
    User otherUser;
    Topster topster;
    Topster otherTopster;

    @BeforeEach
    void init() {
        user = User.builder()
            .nickname("닉네임")
            .email("abc@naver.com")
            .intro("한 줄 소개")
            .password("1234")
            .username("userA")
            .role(UserRoleEnum.USER)
            .build();
        otherUser = User.builder()
            .nickname("닉네임2")
            .email("abc2@naver.com")
            .intro("한 줄 소개2")
            .password("1234")
            .username("userB")
            .role(UserRoleEnum.USER)
            .build();
        topster = Topster.builder()
            .title("제목")
            .user(user)
            .build();
        otherTopster = Topster.builder()
            .title("제목2")
            .user(otherUser)
            .build();
        user = userRepository.save(user);
        otherUser = userRepository.save(otherUser);
        topster = topsterRepository.save(topster);
        otherTopster = topsterRepository.save(otherTopster);
    }

    @Test
    void 검색_조건을_통해_데이터를_조회할_수_있다() {
        // given
        postRepository.save(Post.builder()
            .title("제목1")
            .content("내용1")
            .user(user)
            .topster(topster)
            .build());
        postRepository.save(Post.builder()
            .title("제목2")
            .content("내용2")
            .user(user)
            .topster(topster)
            .build());
        postRepository.save(Post.builder()
            .title("제목3")
            .content("내용3")
            .user(user)
            .topster(topster)
            .build());
        postRepository.save(Post.builder()
            .title("검색 x")
            .content("검색 x")
            .user(user)
            .topster(topster)
            .build());

        PostSearchCond cond = PostSearchCond.builder()
            .key("title")
            .query("제목")
            .build();

        PostPageReq pageReq = PostPageReq.builder()
            .page(1)
            .max(2)
            .build();

        PostSortReq sortReq = PostSortReq.builder()
            .sortBy("createdAt")
            .asc(false)
            .build();

        // when
        Page<PostListRes> res = postRepository.getPostList(cond, pageReq.toPageable(), sortReq);
        List<PostListRes> result = res.getContent();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("제목3");
        assertThat(result.get(1).getTitle()).isEqualTo("제목2");

    }

    @Test
    void 내_게시글을_검색_조건을_통해_검색할_수_있다() {
        // given
        postRepository.save(Post.builder()
            .title("제목1")
            .content("내용1")
            .user(user)
            .topster(topster)
            .build());
        postRepository.save(Post.builder()
            .title("제목2")
            .content("내용2")
            .user(otherUser)
            .topster(otherTopster)
            .build());
        postRepository.save(Post.builder()
            .title("제목3")
            .content("내용3")
            .user(user)
            .topster(topster)
            .build());
        postRepository.save(Post.builder()
            .title("검색 x")
            .content("검색 x")
            .user(otherUser)
            .topster(otherTopster)
            .build());

        PostSearchCond cond = PostSearchCond.builder()
            .key("title")
            .query("제목")
            .build();

        PostPageReq pageReq = PostPageReq.builder()
            .page(1)
            .max(2)
            .build();

        PostSortReq sortReq = PostSortReq.builder()
            .sortBy("createdAt")
            .asc(false)
            .build();

        // when
        Page<PostListRes> res = postRepository.getMyPosts(user.getId(), cond, pageReq.toPageable(), sortReq);
        List<PostListRes> result = res.getContent();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("제목3");
        assertThat(result.get(1).getTitle()).isEqualTo("제목1");

    }

}