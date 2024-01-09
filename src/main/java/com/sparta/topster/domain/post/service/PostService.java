package com.sparta.topster.domain.post.service;

import com.sparta.topster.domain.post.dto.request.PostCreateReq;
import com.sparta.topster.domain.post.dto.request.PostUpdateReq;
import com.sparta.topster.domain.post.dto.response.PostDeatilRes;
import com.sparta.topster.domain.post.entity.Post;
import com.sparta.topster.domain.post.exception.PostException;
import com.sparta.topster.domain.post.repository.PostRepository;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.global.exception.ServiceException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final TopsterService topsterService;

    public Long save(PostCreateReq req, Long topsterId, User user) {
        Topster topster = topsterService.getTopster(topsterId);
        Post post = postRepository.save(Post.builder()
            .title(req.title())
            .content(req.content())
            .topster(topster)
            .user(user)
            .build());

        return post.getId();
    }


    public Long update(PostUpdateReq req, Long id, Long userId) {
        Post post = getUserPost(getPost(id), userId);
        post.update(req.title(), req.content());
        return post.getId();
    }


    public Long delete(Long id, Long userId) {
        Post post = getUserPost(getPost(id), userId);
        postRepository.delete(post);
        return id;
    }


    public PostDeatilRes getPostDetail(Long id) {
        Post post = postRepository.findByIdFetchJoinTopsterAndUser(id)
            .orElseThrow(() -> new ServiceException(PostException.NOT_FOUND));
        return PostDeatilRes.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .nickname(post.getUser().getNickname())
            .topsterId(post.getTopster().getId())
            .createdAt(dateFormat(post.getCreatedAt()))
            .build();
    }


    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> {
            throw new ServiceException(PostException.NOT_FOUND);
        });
    }


    private Post getUserPost(Post post, Long userId) {
        if (post.getUser().getId() != userId) {
            throw new ServiceException(PostException.AccessDeniedError);
        }
        return post;
    }

    private String dateFormat(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
    }
}
