package com.quipux.playlist.application.services.playlist;

import com.quipux.playlist.domain.ports.PlayListRepository;
import com.quipux.playlist.infraestructure.in.exception.PlayListValidationException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DeletePlayListService {

    private final PlayListRepository playListRepository;

    public DeletePlayListService(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    public void execute(String name) {
        if (name == null || name.isBlank()) {
            throw new PlayListValidationException("El nombre no puede estar vacío");
        }
        if (!playListRepository.existsByName(name)) {
            throw new NoSuchElementException("La lista no existe");
        }
        playListRepository.deleteByName(name);
    }
}
