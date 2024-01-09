package com.sparta.topster.domain.topster.entity;


import com.sparta.topster.domain.BaseEntity;
import jakarta.persistence.*;

@Table(name = "tb_topster")
@Entity
public class Topster extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
