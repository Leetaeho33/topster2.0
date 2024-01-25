package com.sparta.topster.domain.album.entity;

import com.sparta.topster.domain.song.entity.Song;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_album")
@Getter
@NoArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String artist;
    //relase -> MySQL 예약어
    @Column
    String releaseDate;
    @Column
    String image;

    @OneToMany(mappedBy = "album", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Song> songList;


    @Builder
    public Album(String title, String artist, String release, String image) {
        this.title = title;
        this.artist = artist;
        this.releaseDate = release;
        this.image = image;
        this.songList = new ArrayList<>();
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}
