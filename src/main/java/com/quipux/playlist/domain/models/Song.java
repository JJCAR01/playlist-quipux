package com.quipux.playlist.domain.models;

import lombok.Data;

@Data
public class Song {

    private String title;
    private String artist;
    private String album;
    private Integer anno;
    private String genre;

    public Song(String title, String artist, String album, Integer anno, String genre) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.anno = anno;
        this.genre = genre;
    }
}
