package com.sparta.topster.domain.post.repository;

import com.sparta.topster.domain.post.dto.request.PostSearchCond;
import com.sparta.topster.domain.post.dto.request.PostSortReq;
import com.sparta.topster.domain.post.dto.response.PostListRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQueryDslRepository {

    Page<PostListRes> getPostList(PostSearchCond cond, Pageable pageable, PostSortReq sortReq);

    Page<PostListRes> getMyPosts(Long id, PostSearchCond cond, Pageable pageable, PostSortReq sortReq);
}
