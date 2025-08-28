package com.quipux.playlist.infraestructure.out.db.entities;


import com.quipux.playlist.domain.models.Song;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "playlists")
public class PlayListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SongEntity> songs = new ArrayList<>();

    public PlayListEntity() {

    }
}
