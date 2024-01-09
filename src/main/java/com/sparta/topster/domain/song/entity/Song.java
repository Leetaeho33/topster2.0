package com.sparta.topster.domain.song.entity;

import com.sparta.topster.domain.album.entity.Album;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_song")
@NoArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String songname;

    @ManyToOne
    @JoinColumn(name = "album_id")
    Album album;

    @Builder
    public Song(String songname) {
        this.songname = songname;
    }
    public void setAlbum(Album album){
        this.album = album;
    }
}
