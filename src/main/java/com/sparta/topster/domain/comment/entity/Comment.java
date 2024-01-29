package com.sparta.topster.domain.comment.entity;

import com.sparta.topster.domain.BaseEntity;
import com.sparta.topster.domain.post.entity.Post;
import com.sparta.topster.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    @ManyToOne
    private User user;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn
    @ManyToOne
    private Post post;

    @Builder
    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    public void update(String comment) {
        this.content = comment;
    }
}
