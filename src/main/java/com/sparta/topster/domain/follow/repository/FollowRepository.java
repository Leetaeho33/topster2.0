package com.sparta.topster.domain.follow.repository;

import com.sparta.topster.domain.follow.entity.Follow;
import com.sparta.topster.domain.follow.entity.FollowId;
import com.sparta.topster.domain.user.entity.User;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

    Optional<Follow> findByToFollowAndFromFollow(User toFollow, User fromFollow);
    List<Follow> findAllByFromFollow(User fromFollow);

    List<Follow> findAllByToFollow(User toFollow);
}
