package com.quipux.playlist.infraestructure.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quipux.playlist.application.services.playlist.CreatePlayListService;
import com.quipux.playlist.application.services.playlist.DeletePlayListService;
import com.quipux.playlist.application.services.playlist.GetPlayListByNameService;
import com.quipux.playlist.application.services.playlist.GetPlayListService;
import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.infraestructure.in.dto.PlayListDto;
import com.quipux.playlist.infraestructure.in.mapper.PlayListMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@WebMvcTest(controllers = PlayListController.class)
class PlayListControllerTest {

    @Mock
    private GetPlayListService getPlayListService;
    @Mock
    private CreatePlayListService createPlayListService;
    @Mock
    private GetPlayListByNameService getPlayListByNameService;
    @Mock
    private DeletePlayListService deletePlayListService;

    @InjectMocks
    private PlayListController controller;

    private PlayList playlist;
    private PlayListDto playlistDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playlist = new PlayList("Rock Classics", "Classic rock hits", List.of());
        playlistDto = PlayListMapper.toDto(playlist);
    }

    @Test
    void testGetAllPlaylists() {
        when(getPlayListService.execute()).thenReturn(List.of(playlist));

        ResponseEntity<List<PlayListDto>> response = controller.getAllPlaylist();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Rock Classics", response.getBody().getFirst().getName());

        verify(getPlayListService, times(1)).execute();
    }
}
