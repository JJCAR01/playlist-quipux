package com.quipux.playlist.application.playlist;

import com.quipux.playlist.application.services.playlist.CreatePlayListService;
import com.quipux.playlist.application.services.playlist.GetPlayListByNameService;
import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.domain.ports.PlayListRepository;
import com.quipux.playlist.infraestructure.in.exception.PlayListValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetPlayListByNameServiceTest {

    private PlayListRepository playListRepository;
    private GetPlayListByNameService getPlayListByNameService;

    @BeforeEach
    void setUp() {
        playListRepository = mock(PlayListRepository.class);
        getPlayListByNameService = new GetPlayListByNameService(playListRepository);
    }

    @Test
    void getPlayListWithNameEmpty() throws PlayListValidationException {
        // given
        PlayList playlist = new PlayList();
        playlist.setName("   "); // blank

        // when + then
        PlayListValidationException exception = assertThrows(
                PlayListValidationException.class,
                () -> getPlayListByNameService.execute(playlist.getName())
        );

        assertEquals("El nombre no puede estar vacío", exception.getMessage());
        verify(playListRepository, never()).save(any());
    }
}
