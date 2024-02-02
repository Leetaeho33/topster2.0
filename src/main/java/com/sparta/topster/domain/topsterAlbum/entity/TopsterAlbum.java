package com.sparta.topster.domain.topsterAlbum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.topster.domain.BaseEntity;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.topster.entity.Topster;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Table(name = "tb_topster_album")
@Entity
@Getter
@NoArgsConstructor
public class TopsterAlbum extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "topster_id")
    private Topster topster;

    @Builder
    public TopsterAlbum(Album album, Topster topster) {
        this.album = album;
        this.topster = topster;
    }
}
