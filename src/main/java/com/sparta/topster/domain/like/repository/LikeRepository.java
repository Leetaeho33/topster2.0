package com.sparta.topster.domain.like.repository;

import com.sparta.topster.domain.like.entity.Like;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Like.class, idClass = Long.class)
public interface LikeRepository {


  void delete(Like alreadyLike);

  void save(Like like);

  Optional<Like> findByTopsterIdAndUserId(Long topsterId, Long userId);
  boolean existsByTopsterIdAndUserId(Long topsterId, Long userId);

}
