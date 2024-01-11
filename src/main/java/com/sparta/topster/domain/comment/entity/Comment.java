package com.sparta.topster.domain.comment.entity;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_comment")
public class Comment {

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

    public void save(CommentModifyReq commentModifyReq) {
      this.content = commentModifyReq.getComment();
    }

}
