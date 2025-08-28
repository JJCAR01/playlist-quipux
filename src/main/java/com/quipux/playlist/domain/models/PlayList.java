package com.quipux.playlist.domain.models;

import lombok.Data;

import java.util.List;

@Data
public class PlayList {
    private String name;
    private String description;
    private List<Song> songs;

    public PlayList(String name, String description, List<Song> songs) {
        this.name = name;
        this.description = description;
        this.songs = songs;
    }

    public PlayList() {

    }
}
