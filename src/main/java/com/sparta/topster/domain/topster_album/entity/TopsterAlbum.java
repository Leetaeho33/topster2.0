package com.sparta.topster.domain.topster_album.entity;

import com.sparta.topster.domain.BaseEntity;
import com.sparta.topster.domain.album.entity.Album;
import com.sparta.topster.domain.topster.entity.Topster;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tb_topster_album")
@Entity
@Getter
@NoArgsConstructor
public class TopsterAlbum extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "album_id")
    Album album;

    @ManyToOne
    @JoinColumn(name = "topster_id")
    Topster topster;

    @Builder
    public TopsterAlbum(Album album, Topster topster) {
        this.album = album;
        this.topster = topster;
    }
}
