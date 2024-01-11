//package com.sparta.topster.domain.post.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import com.sparta.topster.domain.post.dto.request.PostCreateReq;
//import com.sparta.topster.domain.post.dto.request.PostPageReq;
//import com.sparta.topster.domain.post.dto.request.PostSearchCond;
//import com.sparta.topster.domain.post.dto.request.PostSortReq;
//import com.sparta.topster.domain.post.dto.request.PostUpdateReq;
//import com.sparta.topster.domain.post.dto.response.PostDeatilRes;
//import com.sparta.topster.domain.post.dto.response.PostListRes;
//import com.sparta.topster.domain.post.entity.Post;
//import com.sparta.topster.domain.post.repository.PostRepository;
//import com.sparta.topster.domain.topster.entity.Topster;
//import com.sparta.topster.domain.topster.service.TopsterService;
//import com.sparta.topster.domain.user.entity.User;
//import com.sparta.topster.domain.user.entity.UserRoleEnum;
//import com.sparta.topster.global.exception.ServiceException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.concurrent.atomic.AtomicLong;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.util.ReflectionTestUtils;
//
//class PostServiceTest {
//
//    PostService postService;
//    User user;
//    User otherUser;
//    Topster topster;
//
//
//    @BeforeEach
//    void init() {
//        postService = new PostService(new FakePostRepository(), new FakeTopsterService());
//        topster = new Topster();
//        ReflectionTestUtils.setField(topster, "id", 1L);
//        user = User.builder()
//            .nickname("닉네임")
//            .email("abc@naver.com")
//            .intro("한 줄 소개")
//            .password("1234")
//            .username("userA")
//            .role(UserRoleEnum.USER)
//            .build();
//        otherUser = User.builder()
//            .nickname("닉네임2")
//            .email("abc2@naver.com")
//            .intro("한 줄 소개2")
//            .password("1234")
//            .username("userB")
//            .role(UserRoleEnum.USER)
//            .build();
//        ReflectionTestUtils.setField(user, "id", 1L);
//        ReflectionTestUtils.setField(otherUser, "id", 2L);
//    }
//
//
//    @Test
//    void 생성_요청_DTO를_통해_생성하고_id값을_반환_받을_수_있다() {
//        // given
//        PostCreateReq req = PostCreateReq.builder()
//            .title("제목")
//            .content("내용")
//            .build();
//
//        // when
//        Long postId = postService.save(req, 1L, user);
//
//        // then
//        assertThat(postId).isNotNull();
//    }
//
//    @Test
//    void 게시글_수정을_요청할_때_id에_해당하는_데이터가_없으면_예외를_반환한다() {
//        // given
//        PostUpdateReq req = PostUpdateReq.builder()
//            .title("제목 수정")
//            .content("내용 수정")
//            .build();
//
//        // when
//        // then
//        assertThatThrownBy(() -> postService.update(req, 1L, user.getId()))
//            .isInstanceOf(ServiceException.class)
//            .hasMessage("해당 게시글이 존재하지 않습니다.");
//    }
//
//    @Test
//    void 게시글_수정을_작성자가_아닌_다른_유저가_요청하면_예외를_반환한다() {
//        // given
//        PostCreateReq saveReq = PostCreateReq.builder()
//            .title("제목")
//            .content("내용")
//            .build();
//        Long saveId = postService.save(saveReq, topster.getId(), user);
//        PostUpdateReq req = PostUpdateReq.builder()
//            .title("제목 수정")
//            .content("내용 수정")
//            .build();
//
//        // when
//        // then
//        assertThatThrownBy(() -> postService.update(req, saveId, otherUser.getId()))
//            .isInstanceOf(ServiceException.class)
//            .hasMessage("해당 게시글의 작성자가 아닙니다.");
//    }
//
//    @Test
//    void 수정_요청_DTO를_통해_본인의_게시글을_수정할_수_있다() {
//        // given
//        PostCreateReq saveReq = PostCreateReq.builder()
//            .title("제목")
//            .content("내용")
//            .build();
//        Long saveId = postService.save(saveReq, topster.getId(), user);
//        PostUpdateReq req = PostUpdateReq.builder()
//            .title("제목 수정")
//            .content("내용 수정")
//            .build();
//
//        // when
//        postService.update(req, saveId, user.getId());
//        Post result = postService.getPost(saveId);
//
//        // then
//        assertThat(result.getTitle()).isEqualTo(req.title());
//        assertThat(result.getContent()).isEqualTo(req.content());
//    }
//
//    @Test
//    void 게시글_삭제를_요청할_때_id에_해당하는_데이터가_없으면_예외를_반환한다() {
//        // given
//        PostCreateReq saveReq = PostCreateReq.builder()
//            .title("제목")
//            .content("내용")
//            .build();
//        Long saveId = postService.save(saveReq, topster.getId(), user);
//
//        // when
//        // then
//        assertThatThrownBy(() -> postService.delete(saveId + 1, user.getId()))
//            .isInstanceOf(ServiceException.class)
//            .hasMessage("해당 게시글이 존재하지 않습니다.");
//    }
//
//    @Test
//    void 게시글_삭제를_작성자가_아닌_다른_유저가_요청하면_예외를_반환한다() {
//        // given
//        PostCreateReq saveReq = PostCreateReq.builder()
//            .title("제목")
//            .content("내용")
//            .build();
//        Long saveId = postService.save(saveReq, topster.getId(), user);
//
//        // when
//        // then
//        assertThatThrownBy(() -> postService.delete(saveId, otherUser.getId()))
//            .isInstanceOf(ServiceException.class)
//            .hasMessage("해당 게시글의 작성자가 아닙니다.");
//    }
//
//    @Test
//    void 삭제할_게시글id를_통해_본인의_게시글을_삭제할_수_있다() {
//        // given
//        PostCreateReq saveReq = PostCreateReq.builder()
//            .title("제목")
//            .content("내용")
//            .build();
//        Long saveId = postService.save(saveReq, topster.getId(), user);
//
//        // when
//        // then
//        Long deleteId = postService.delete(saveId, user.getId());
//        assertThatThrownBy(() -> postService.getPost(deleteId))
//            .isInstanceOf(ServiceException.class)
//            .hasMessage("해당 게시글이 존재하지 않습니다.");
//    }
//
//    @Test
//    void 게시글_id_를_통해_상세_조회를_할_때_id에_해당하는_데이터가_없으면_예외를_반환한다() {
//        // given
//        // when
//        // then
//        assertThatThrownBy(() -> postService.getPostDetail(1L))
//            .isInstanceOf(ServiceException.class)
//            .hasMessage("해당 게시글이 존재하지 않습니다.");
//    }
//
//    @Test
//    void 게시글_id를_통해_탑스터id와_유저의_닉네임이_함께_존재하는_상세_조회를_할_수_있다() {
//        // given
//        PostCreateReq saveReq = PostCreateReq.builder()
//            .title("제목")
//            .content("내용")
//            .build();
//        Long saveId = postService.save(saveReq, topster.getId(), user);
//
//        // when
//        PostDeatilRes result = postService.getPostDetail(saveId);
//
//        // then
//        assertThat(result.id()).isNotNull();
//        assertThat(result.title()).isEqualTo(saveReq.title());
//        assertThat(result.content()).isEqualTo(saveReq.content());
//        assertThat(result.content()).isEqualTo(saveReq.content());
//        assertThat(result.topsterId()).isEqualTo(topster.getId());
//        assertThat(result.nickname()).isEqualTo(user.getNickname());
//    }
//
//    @Test
//    void 검색_조건을_통해_게시글_목록을_반환받을_수_있다() {
//        // given
//        postService.save(PostCreateReq.builder()
//            .title("제목1")
//            .content("내용1")
//            .build(), topster.getId(), user
//        );
//        postService.save(PostCreateReq.builder()
//            .title("제목2")
//            .content("내용2")
//            .build(), topster.getId(), user
//        );
//        postService.save(PostCreateReq.builder()
//            .title("제목3")
//            .content("내용3")
//            .build(), topster.getId(), user
//        );
//        postService.save(PostCreateReq.builder()
//            .title("검색x")
//            .content("검색x")
//            .build(), topster.getId(), user
//        );
//
//        PostSearchCond cond = PostSearchCond.builder()
//            .key("title")
//            .query("제목")
//            .build();
//
//        PostPageReq pageReq = PostPageReq.builder()
//            .page(1)
//            .max(2)
//            .build();
//
//        PostSortReq sortReq = PostSortReq.builder()
//            .sortBy("createdAt")
//            .asc(false)
//            .build();
//
//        // when
//        Page<PostListRes> res = postService.getPostList(cond, pageReq, sortReq);
//        List<PostListRes> content = res.getContent();
//
//        // then
//        assertThat(content.size()).isEqualTo(2);
//        for (PostListRes postListRes : content) {
//            System.out.println(postListRes.getCreatedAt());
//        }
//        assertThat(content.get(0).getTitle()).isEqualTo("제목3");
//        assertThat(content.get(1).getTitle()).isEqualTo("제목2");
//    }
//
//
//    /**
//     * PostService에서 필요한 의존성 Fake 클래스 외부 class로 뺴서 만들면 @SpringBoot 테스트를 돌릴 때 의존성 주입에서 에러 발생하기 때문에 내부
//     * 클래스로 만듦
//     */
//    private class FakePostRepository implements PostRepository {
//
//        AtomicLong generatedId = new AtomicLong(0);
//        List<Post> data = new ArrayList<>();
//
//        @Override
//        public Post save(Post post) {
//            if (post.getId() == null || post.getId() == 0) {
//                ReflectionTestUtils.setField(post, "id", generatedId.incrementAndGet());
//                ReflectionTestUtils.setField(post, "createdAt",
//                    LocalDateTime.now().plusSeconds(generatedId.get()));
//                data.add(post);
//                return post;
//            }
//            data.removeIf(item -> item.getId().equals(post.getId()));
//            data.add(post);
//            return post;
//        }
//
//        @Override
//        public Optional<Post> findById(Long id) {
//            return data.stream().filter(item -> item.getId().equals(id)).findAny();
//        }
//
//        @Override
//        public void delete(Post post) {
//            data.removeIf(item -> item.getId().equals(post.getId()));
//        }
//
//        @Override
//        public Optional<Post> findByIdFetchJoinTopsterAndUser(Long id) {
//            return data.stream().filter(item -> item.getId().equals(id)).findAny();
//        }
//
//        @Override
//        public Page<PostListRes> getPostList(PostSearchCond cond, Pageable pageable,
//            PostSortReq sortReq) {
//
//            String key = cond.key();
//            String query = cond.query();
//            String sortBy = sortReq.getSortBy();
//            Boolean asc = sortReq.getAsc();
//            List<Post> posts = new ArrayList<>();
//
//            data.forEach(item -> {
//                switch (key) {
//                    case "title":
//                        if (item.getTitle().contains(query)) {
//                            posts.add(item);
//                        }
//                        break;
//                    case "content":
//                        if (item.getContent().contains(query)) {
//                            posts.add(item);
//                        }
//                        break;
//                    case "author":
//                        if (item.getUser().getNickname().contains(query)) {
//                            posts.add(item);
//                        }
//                        break;
//                    default:
//                        posts.add(item);
//                }
//            });
//
//            List<PostListRes> res = new ArrayList<>();
//
//            posts.forEach(post -> res.add(PostListRes.builder()
//                .id(post.getId())
//                .nickname(post.getUser().getNickname())
//                .title(post.getTitle())
//                .createdAt(post.getCreatedAt())
//                .build())
//            );
//
//            Comparator<PostListRes> comparator;
//            switch (sortBy) {
//                case "createdAt":
//                    comparator = Comparator.comparing(PostListRes::getCreatedAt);
//                    break;
//                case "title":
//                    comparator = Comparator.comparing(PostListRes::getTitle);
//                    break;
//                default:
//                    comparator = Comparator.comparing(PostListRes::getCreatedAt);
//            }
//
//            if (!asc) {
//                comparator = comparator.reversed();
//            }
//
//            Collections.sort(res, comparator);
//
//            int offset = (int) pageable.getOffset();
//            int limit = pageable.getPageSize();
//
//            return new PageImpl<>(
//                res.subList(offset, Math.min(offset + limit, posts.size())), pageable,
//                data.size());
//        }
//    }
//
//    private class FakeTopsterService extends TopsterService {
//
//        @Override
//        public Topster getTopster(Long topsterId) {
//            return topster;
//        }
//    }
//}