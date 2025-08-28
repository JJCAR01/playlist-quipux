package com.quipux.playlist.infraestructure.in.controller;

import com.quipux.playlist.application.services.playlist.CreatePlayListService;
import com.quipux.playlist.application.services.playlist.DeletePlayListService;
import com.quipux.playlist.application.services.playlist.GetPlayListByNameService;
import com.quipux.playlist.application.services.playlist.GetPlayListService;
import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.infraestructure.in.dto.PlayListDto;
import com.quipux.playlist.infraestructure.in.mapper.PlayListMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lists")
public class PlayListController {


    private final GetPlayListService getPlayListService;
    private final CreatePlayListService createPlayListService;
    private final GetPlayListByNameService getPlayListByNameService;
    private final DeletePlayListService deletePlayListService;;

    public PlayListController(GetPlayListService getPlayListService, CreatePlayListService createPlayListService, GetPlayListByNameService getPlayListByNameService, DeletePlayListService deletePlayListService) {
        this.getPlayListService = getPlayListService;
        this.createPlayListService = createPlayListService;
        this.getPlayListByNameService = getPlayListByNameService;
        this.deletePlayListService = deletePlayListService;
    }

    @PostMapping
    public ResponseEntity<PlayListDto> createPlaylist(@RequestBody PlayListDto playlistDto) {
        PlayList playlist = PlayListMapper.toDomain(playlistDto);
        PlayList created = createPlayListService.execute(playlist);
        PlayListDto response = PlayListMapper.toDto(created);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{name}")
                .buildAndExpand(response.getName())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PlayListDto>> getAllPlaylist() {
        List<PlayListDto> playlists = getPlayListService.execute().stream()
                .map(PlayListMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playlists);
    }


    @GetMapping("/{listName}")
    public ResponseEntity<PlayListDto> getPlaylistByName(@PathVariable String listName){
        if (listName == null || listName.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return getPlayListByNameService.execute(listName)
                .map(entity -> ResponseEntity.ok(PlayListMapper.toDto(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{listName}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable String listName) {
        try {
            deletePlayListService.execute(listName);
            return ResponseEntity.noContent().build(); // Devuelve 204
        } catch (RuntimeException e) { // Idealmente una excepción más específica
            return ResponseEntity.notFound().build(); // Devuelve 404
        }
    }




}
