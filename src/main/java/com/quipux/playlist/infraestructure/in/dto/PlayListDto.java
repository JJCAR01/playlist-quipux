package com.quipux.playlist.infraestructure.in.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayListDto {
    private String name;
    private String description;
    private List<SongDto> songs;
}
