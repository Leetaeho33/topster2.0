package com.sparta.topster.domain.like.entity;

import com.sparta.topster.domain.BaseEntity;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.user.entity.User;
import com.sparta.topster.global.security.UserDetailsImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_topster_like")
@NoArgsConstructor
public class Like extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "topster_id")
    @ManyToOne
    private Topster topster;

    @Column(nullable = false)
    private boolean status; // true : 좋아요 , false = 좋아요 취소

    public Like(Topster topster, UserDetailsImpl userDetails) {
        this.topster = topster;
        this.user = userDetails.getUser();

    }
}
