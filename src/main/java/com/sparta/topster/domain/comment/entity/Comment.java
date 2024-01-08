package com.sparta.topster.domain.comment.entity;

import com.sparta.topster.domain.comment.dto.CommentModifyReq;
import com.sparta.topster.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column
    private String comment;

    @JoinColumn
    @ManyToOne
    private User user;

//    @JoinColumn
//    @ManyToOne
//    private Board board;

    public void save(CommentModifyReq commentModifyReq) {
      this.comment = commentModifyReq.getComment();
    }

}
