package com.quipux.playlist.infraestructure.out.db.jpa;

import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.domain.models.Song;
import com.quipux.playlist.domain.ports.PlayListRepository;
import com.quipux.playlist.infraestructure.in.mapper.PlayListMapper;
import com.quipux.playlist.infraestructure.out.db.entities.PlayListEntity;
import com.quipux.playlist.infraestructure.out.db.entities.SongEntity;
import com.quipux.playlist.infraestructure.out.db.jpa.repository.H2PlayListRepository;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaPlayListRepository implements PlayListRepository {

    private final H2PlayListRepository h2PlayListRepository;

    public JpaPlayListRepository(H2PlayListRepository h2PlayListRepository) {
        this.h2PlayListRepository = h2PlayListRepository;
    }

    @Override
    public PlayList save(PlayList playlist) {
        PlayListEntity entity = new PlayListEntity();
        entity.setName(playlist.getName());
        entity.setDescription(playlist.getDescription());

        // Convertir canciones del modelo a entidades
        List<SongEntity> songEntities = playlist.getSongs()
                .stream()
                .map(song -> {
                    SongEntity se = new SongEntity();
                    se.setTitle(song.getTitle());
                    se.setArtist(song.getArtist());
                    se.setAlbum(song.getAlbum());
                    se.setAnno(song.getAnno());
                    se.setGenre(song.getGenre());

                    se.setPlaylist(entity);
                    return se;
                })
                .toList();

        entity.setSongs(songEntities);

        // Guardar en repositorio
        PlayListEntity savedEntity = h2PlayListRepository.save(entity);

        // Convertir de vuelta a dominio si lo necesitas
        return new PlayList(
                savedEntity.getName(),
                savedEntity.getDescription(),
                savedEntity.getSongs()
                        .stream()
                        .map(se -> new Song(se.getTitle(), se.getArtist(),se.getAlbum(), se.getAnno(), se.getGenre()))
                        .toList()
        );
    }

    @Override
    public Optional<PlayList> findByName(String name) {
        return h2PlayListRepository.findByName(name)
                .map(entity -> new PlayList(
                        entity.getName(),
                        entity.getDescription(),
                        entity.getSongs().stream()
                                .map(songEntity -> new com.quipux.playlist.domain.models.Song(
                                        songEntity.getTitle(),
                                        songEntity.getArtist(),
                                        songEntity.getAlbum(),
                                        songEntity.getAnno(),
                                        songEntity.getGenre()
                                ))
                                .toList()
                ));
    }


    @Override
    public List<PlayList> findAll() {
        return h2PlayListRepository.findAll().stream()
                .map(entity -> new PlayList(
                        entity.getName(),
                        entity.getDescription(),
                        entity.getSongs().stream()
                                .map(songEntity -> new Song(
                                        songEntity.getTitle(),
                                        songEntity.getArtist(),
                                        songEntity.getAlbum(),
                                        songEntity.getAnno(),
                                        songEntity.getGenre()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByName(String name) {
        Optional<PlayListEntity> playlist = h2PlayListRepository.findByName(name);
        if (playlist.isPresent()) {
            h2PlayListRepository.delete(playlist.get());
        } else {
            throw new RuntimeException("Playlist not found");
        }
    }

    @Override
    public boolean existsByName(String name) {
        return h2PlayListRepository.existsByName(name);
    }
}
