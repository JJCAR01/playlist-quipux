package com.quipux.playlist.application.playlist;

import com.quipux.playlist.application.services.playlist.DeletePlayListService;
import com.quipux.playlist.domain.ports.PlayListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class DeletePlayListServiceTest {

    private PlayListRepository playListRepository;
    private DeletePlayListService deletePlayListService;

    @BeforeEach
    void setUp() {
        playListRepository = mock(PlayListRepository.class);
        deletePlayListService = new DeletePlayListService(playListRepository);
    }

    @Test
    void deletePlaylistWhenExists() {
        // given
        String playlistName = "Rock Classics";
        when(playListRepository.existsByName(playlistName)).thenReturn(true);

        // when
        deletePlayListService.execute(playlistName);

        // then
        verify(playListRepository).existsByName(playlistName);
        verify(playListRepository).deleteByName(playlistName);
    }

    @Test
    void deleteWhenPlaylistDoesNotExist() {
        // given
        String playlistName = "Lista 1";
        when(playListRepository.existsByName(playlistName)).thenReturn(false);

        // when + then
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> deletePlayListService.execute(playlistName)
        );

        assertEquals("La lista no existe", exception.getMessage());
        verify(playListRepository, never()).deleteByName(any());
    }
}
