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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Table(name = "tb_topster_like")
@NoArgsConstructor
public class Like extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "topster_id")
    @ManyToOne
    private Topster topster;


    @Builder
    public Like(Topster topster, User user) {
        this.topster = topster;
        this.user = user;
    }


}
