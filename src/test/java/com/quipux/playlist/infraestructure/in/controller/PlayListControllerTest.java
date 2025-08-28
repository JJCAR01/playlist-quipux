package com.quipux.playlist.infraestructure.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quipux.playlist.application.services.playlist.CreatePlayListService;
import com.quipux.playlist.application.services.playlist.DeletePlayListService;
import com.quipux.playlist.application.services.playlist.GetPlayListByNameService;
import com.quipux.playlist.application.services.playlist.GetPlayListService;
import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.infraestructure.in.dto.PlayListDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PlayListController.class)
class PlayListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // para serializar JSON

    @MockitoBean
    private GetPlayListService getPlayListService;

    @MockitoBean
    private CreatePlayListService createPlayListService;

    @MockitoBean
    private GetPlayListByNameService getPlayListByNameService;

    @MockitoBean
    private DeletePlayListService deletePlayListService;

    @Test
    void shouldCreatePlaylist() throws Exception {
        PlayList playlist = new PlayList();
        playlist.setName("Rock Classics");

        PlayListDto dto = new PlayListDto();
        dto.setName("Rock Classics");

        when(createPlayListService.execute(any(PlayList.class))).thenReturn(playlist);

        mockMvc.perform(post("/lists")
                        .with(httpBasic("admin", "admin123")) // ✅ credenciales de tu SecurityConfig
                        .with(csrf()) // necesario si no desactivaste CSRF
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Rock Classics\",\"description\":null,\"songs\":null}"))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnAllPlaylists() throws Exception {
        PlayList playlist = new PlayList();
        playlist.setName("Pop 90s");

        when(getPlayListService.execute()).thenReturn(List.of(playlist));

        mockMvc.perform(get("/lists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pop 90s"));
    }

    @Test
    void shouldReturnPlaylistByName() throws Exception {
        PlayList playlist = new PlayList();
        playlist.setName("Chill Vibes");

        when(getPlayListByNameService.execute("Chill Vibes")).thenReturn(Optional.of(playlist));

        mockMvc.perform(get("/lists/Chill Vibes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chill Vibes"));
    }

    @Test
    void shouldReturn404WhenPlaylistNotFound() throws Exception {
        when(getPlayListByNameService.execute("Unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/lists/Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeletePlaylist() throws Exception {
        Mockito.doNothing().when(deletePlayListService).execute("Rock Classics");

        mockMvc.perform(delete("/lists/Rock Classics"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentPlaylist() throws Exception {
        Mockito.doThrow(new RuntimeException("not found"))
                .when(deletePlayListService).execute("Unknown");

        mockMvc.perform(delete("/lists/Unknown"))
                .andExpect(status().isNotFound());
    }
}
