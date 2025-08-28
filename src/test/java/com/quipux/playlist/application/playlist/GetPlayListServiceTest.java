package com.quipux.playlist.application.playlist;

import com.quipux.playlist.application.services.playlist.GetPlayListService;
import com.quipux.playlist.domain.models.PlayList;
import com.quipux.playlist.domain.models.Song;
import com.quipux.playlist.domain.ports.PlayListRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GetPlayListServiceTest {

    @Test
    void getListOfPlayLists() {
        // Arrange
        PlayListRepository mockRepository = mock(PlayListRepository.class);
        GetPlayListService service = new GetPlayListService(mockRepository);

        // Crear algunas canciones
        Song song1 = new Song("Song A", "Artista 1","Album 1",2020,"mas");
        Song song2 = new Song("Song B", "Artista 2","Album 1",2025,"mas");
        Song song3 = new Song("Song C", "Artista 3","Album 2",2025,"fem");


        // Crear playlists
        PlayList playlist1 = new PlayList();
        playlist1.setName("Playlist 1");
        playlist1.setDescription("Descripción 1");
        playlist1.setSongs(List.of(song1, song2));

        PlayList playlist2 = new PlayList();
        playlist2.setName("Playlist 2");
        playlist2.setDescription("Descripción 2");
        playlist2.setSongs(List.of(song3));

        List<PlayList> playlists = List.of(playlist1, playlist2);

        // Simular comportamiento del repositorio
        when(mockRepository.findAll()).thenReturn(playlists);

        // Act
        List<PlayList> result = service.execute();

        // Assert
        assertEquals(2, result.size());

        assertEquals("Playlist 1", result.getFirst().getName());
        assertEquals(2, result.get(0).getSongs().size());
        assertEquals("Song A", result.get(0).getSongs().getFirst().getTitle());

        assertEquals("Playlist 2", result.get(1).getName());
        assertEquals(1, result.get(1).getSongs().size());
        assertEquals("Song C", result.get(1).getSongs().getFirst().getTitle());

        // Verifica que se haya llamado al repositorio una vez
        verify(mockRepository, times(1)).findAll();
    }
}
