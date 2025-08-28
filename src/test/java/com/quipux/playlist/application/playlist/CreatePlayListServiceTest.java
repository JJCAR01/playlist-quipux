package com.quipux.playlist.application.playlist;

import com.quipux.playlist.application.services.playlist.CreatePlayListService;
import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.domain.ports.PlayListRepository;
import com.quipux.playlist.infraestructure.in.exception.PlayListValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreatePlayListServiceTest {

    private PlayListRepository playListRepository;
    private CreatePlayListService createPlayListService;

    @BeforeEach
    void setUp() {
        playListRepository = mock(PlayListRepository.class);
        createPlayListService = new CreatePlayListService(playListRepository);
    }

    @Test
    void createPlayListWhenValid() {
        // given
        PlayList playlist = new PlayList();
        playlist.setName("Lista 1");

        when(playListRepository.existsByName("Lista 1")).thenReturn(false);
        when(playListRepository.save(playlist)).thenReturn(playlist);

        // when
        PlayList result = createPlayListService.execute(playlist);

        // then
        assertNotNull(result);
        assertEquals("Lista 1", result.getName());
        verify(playListRepository).existsByName("Lista 1");
        verify(playListRepository).save(playlist);
    }

    @Test
    void createPlayListWithNameEmpty() {
        // given
        PlayList playlist = new PlayList();
        playlist.setName("   "); // blank

        // when + then
        PlayListValidationException exception = assertThrows(
                PlayListValidationException.class,
                () -> createPlayListService.execute(playlist)
        );

        assertEquals("El nombre de la lista no puede estar vacío", exception.getMessage());
        verify(playListRepository, never()).save(any());
    }

    @Test
    void createPlayListWhenPlaylistAlreadyExists() {
        // given
        PlayList playlist = new PlayList();
        playlist.setName("Lista 1");

        when(playListRepository.existsByName("Lista 1")).thenReturn(true);

        // when + then
        PlayListValidationException exception = assertThrows(
                PlayListValidationException.class,
                () -> createPlayListService.execute(playlist)
        );

        assertEquals("La lista ya existe", exception.getMessage());
        verify(playListRepository, never()).save(any());
    }
}
