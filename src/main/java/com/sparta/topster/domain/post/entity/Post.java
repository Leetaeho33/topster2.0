package com.sparta.topster.domain.post.entity;

import com.sparta.topster.domain.BaseEntity;
import com.sparta.topster.domain.comment.entity.Comment;
import com.sparta.topster.domain.topster.entity.Topster;
import com.sparta.topster.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topster topster;

    @OneToMany(mappedBy = "comment")
    List<Comment> commentList;

    @Builder
    public Post(String title, String content, User user, Topster topster) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.topster = topster;
    }
    
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
