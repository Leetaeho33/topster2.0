package com.sparta.topster.domain.post.service;

import com.sparta.topster.domain.post.dto.request.PostCreateReq;
import com.sparta.topster.domain.post.entity.Post;
import com.sparta.topster.domain.post.repository.PostRepository;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.topster.service.TopsterService;
import com.sparta.topster.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

}
