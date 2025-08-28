package com.quipux.playlist.infraestructure.in.mapper;

import com.quipux.playlist.domain.models.Song;
import com.quipux.playlist.infraestructure.in.dto.SongDto;

public class SongMapper {

    public static SongDto songDto(Song song) {
        return SongDto.builder()
                .title(song.getTitle())
                .artist(song.getArtist())
                .album(song.getAlbum())
                .anno(song.getAnno())
                .genre(song.getGenre())
                .build();
    }

    public static Song toDomain(SongDto dto) {
        return new Song(
                dto.getTitle(),
                dto.getArtist(),
                dto.getAlbum(),
                dto.getAnno(),
                dto.getGenre()
        );
    }
}
