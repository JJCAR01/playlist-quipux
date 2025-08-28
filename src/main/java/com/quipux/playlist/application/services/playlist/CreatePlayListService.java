package com.quipux.playlist.application.services.playlist;

import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.domain.ports.PlayListRepository;
import com.quipux.playlist.infraestructure.in.exception.PlayListValidationException;
import org.springframework.stereotype.Service;

@Service
public class CreatePlayListService {

    public final PlayListRepository playListRepository;

    public CreatePlayListService(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    public PlayList execute(PlayList playlist) {
        if (playlist.getName() == null || playlist.getName().isBlank()) {
            throw new PlayListValidationException("El nombre de la lista no puede estar vacío");
        }
        if (playListRepository.existsByName(playlist.getName())) {
            throw new PlayListValidationException("La lista ya existe");
        }
        return playListRepository.save(playlist);
    }
}
