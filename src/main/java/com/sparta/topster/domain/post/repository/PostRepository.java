package com.sparta.topster.domain.post.repository;

import com.sparta.topster.domain.post.entity.Post;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Post.class, idClass = Long.class)
public interface PostRepository {

    Post save(Post post);

}
