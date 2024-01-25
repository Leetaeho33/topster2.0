package com.sparta.topster.domain.follow.entity;

import com.sparta.topster.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@IdClass(FollowId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "tb_follow")
public class Follow {

    @EmbeddedId
    private FollowId followId = new FollowId();

    @ManyToOne
    @MapsId("fromFollow")
    private User fromFollow;

    @ManyToOne
    @MapsId("toFollow")
    private User toFollow;

    @Builder
    public Follow(User fromFollow, User toFollow) {
        this.fromFollow = fromFollow;
        this.toFollow = toFollow;
    }
}
