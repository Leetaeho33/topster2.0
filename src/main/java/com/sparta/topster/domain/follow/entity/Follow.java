package com.sparta.topster.domain.follow.entity;

import com.sparta.topster.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "tb_follow")
public class Follow {

    @EmbeddedId
    private FollowId followId = new FollowId();

    @ManyToOne
    @MapsId("follower")
    private User follower;

    @ManyToOne
    @MapsId("following")
    private User following;

    @Builder
    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
