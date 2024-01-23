package com.sparta.topster.domain.topster.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.topster.domain.BaseEntity;
import com.sparta.topster.domain.like.entity.Like;
import com.sparta.topster.domain.topster_album.entity.TopsterAlbum;
import com.sparta.topster.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_topster")
@Entity
@Getter
public class Topster extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @OneToMany(mappedBy = "topster", cascade = CascadeType.PERSIST)
    List<Like> topsterLike = new ArrayList<>();

    @OneToMany(mappedBy = "topster", cascade = CascadeType.PERSIST)
    List<TopsterAlbum> topsterAlbumList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column
    private Long likeCount = 0L;

    @Builder
    public Topster(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void upAndDownLikeCount(Integer l) {
        this.likeCount += l;
    }
}
