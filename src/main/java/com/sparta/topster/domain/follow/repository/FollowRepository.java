package com.sparta.topster.domain.follow.repository;

import com.sparta.topster.domain.follow.entity.Follow;
import com.sparta.topster.domain.follow.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {
}
