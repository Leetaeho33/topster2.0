package com.sparta.topster.domain.topster.entity;


import com.sparta.topster.domain.BaseEntity;
import com.sparta.topster.domain.topster_album.entity.TopsterAlbum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.hibernate.bytecode.internal.bytebuddy.PrivateAccessorException;

import java.util.List;

@NoArgsConstructor
@Table(name = "tb_topster")
@Entity
public class Topster extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @OneToMany(mappedBy = "topster", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<TopsterAlbum> topsterAlbumList;

    @Builder
    public Topster(String title, String content, List<TopsterAlbum> topsterAlbumList) {
        this.title = title;
        this.content = content;
        this.topsterAlbumList = topsterAlbumList;
    }
}
