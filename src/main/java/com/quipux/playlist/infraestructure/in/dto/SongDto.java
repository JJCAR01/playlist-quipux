package com.quipux.playlist.infraestructure.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongDto {

    private String title;
    private String artist;
    private String album;
    private Integer anno;
    private String genre;

}
