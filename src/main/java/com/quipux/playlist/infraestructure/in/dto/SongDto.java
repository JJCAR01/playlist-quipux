package com.quipux.playlist.infraestructure.in.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SongDto {

    private String title;
    private String artist;
    private String album;
    private Integer anno;
    private String genre;
}
