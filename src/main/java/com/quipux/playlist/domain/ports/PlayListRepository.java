package com.quipux.playlist.domain.ports;

import com.quipux.playlist.domain.models.PlayList;

import java.util.List;
import java.util.Optional;

public interface    PlayListRepository {

    PlayList save(PlayList playlist);
    Optional<PlayList> findByName(String name);
    List<PlayList> findAll();
    void deleteByName(String name);
    boolean existsByName(String name);
}
