package com.quipux.playlist.application.services.playlist;

import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.domain.ports.PlayListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPlayListService {

    private final PlayListRepository playListRepository;

    public GetPlayListService(PlayListRepository playListRepository) {
        this.playListRepository = playListRepository;
    }

    public List<PlayList> execute() {
        return playListRepository.findAll();
    }
}
