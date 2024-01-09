package com.sparta.topster.domain.album.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
