package com.sparta.topster.domain.post.repository;

import com.sparta.topster.domain.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Post.class, idClass = Long.class)
public interface PostRepository extends PostQueryDslRepository {

    Post save(Post post);

    Optional<Post> findById(Long id);

    void delete(Post post);

    @Query("select p from Post p join fetch p.topster t join fetch p.user u where p.id = :id")
    Optional<Post> findByIdFetchJoinTopsterAndUser(Long id);
}
