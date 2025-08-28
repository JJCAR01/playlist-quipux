package com.quipux.playlist.infraestructure.in.mapper;

import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.infraestructure.in.dto.PlayListDto;

public class PlayListMapper {
    public static PlayListDto toDto(PlayList playList) {
        return PlayListDto.builder().
                name(playList.getName()).
                description(playList.getDescription()).
                songs(playList.getSongs()
                        .stream()
                        .map(SongMapper::songDto)
                        .toList())
                .build();
    }

    public static PlayList toDomain(PlayListDto dto) {
        return new PlayList(
                dto.getName(),
                dto.getDescription(),
                dto.getSongs().stream()
                        .map(SongMapper::toDomain)
                        .toList()
        );
    }
}
