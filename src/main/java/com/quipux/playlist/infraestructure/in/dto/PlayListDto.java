package com.quipux.playlist.infraestructure.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayListDto {
    private String name;
    private String description;
    private List<SongDto> songs;

}
