package com.quipux.playlist.application.services.playlist;

import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.domain.ports.PlayListRepository;
import com.quipux.playlist.infraestructure.in.exception.PlayListValidationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetPlayListByNameService {

    private final PlayListRepository repository;

    public GetPlayListByNameService(PlayListRepository repository) {
        this.repository = repository;
    }

    public Optional<PlayList> execute(String name) {
        if (name == null || name.isBlank()) {
            throw new PlayListValidationException("El nombre no puede estar vacío");
        }
        return repository.findByName(name);
    }
}
