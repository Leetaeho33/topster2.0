package com.sparta.topster.domain.follow.entity;

import com.sparta.topster.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowId implements Serializable {

    @Serial
    private static final long serialVersionUID = 932813899396136126L;

    @Column(name = "follower_id")
    private Long follower;

    @Column(name = "following_id")
    private Long following;



}
