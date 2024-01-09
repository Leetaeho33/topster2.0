package com.sparta.topster.domain.post.service;

import com.sparta.topster.domain.post.repository.PostRepository;
import com.sparta.topster.domain.topster.service.TopsterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TopsterService topsterService;


}
