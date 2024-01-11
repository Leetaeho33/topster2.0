package com.sparta.topster.domain.comment.entity;

import com.sparta.topster.domain.BaseEntity;
import com.sparta.topster.domain.comment.dto.req.CommentCreateReq;
import com.sparta.topster.domain.comment.dto.req.CommentModifyReq;
import com.sparta.topster.domain.post.entity.Post;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "tb_comment")
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @JoinColumn
    @ManyToOne
    private User user;

    @JoinColumn
    @ManyToOne
    private Post post;


    public Comment(CommentCreateReq commentCreateReq, Post post, UserDetailsImpl userDetails) {
        this.content = commentCreateReq.getContent();
        this.post = post;
        this.user = userDetails.getUser();

    }


    public void update(String comment) {
        this.content = comment;
    }
}
