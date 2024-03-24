package com.tirofortuna.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tirofortuna.controllers.dto.GameDTO;
import com.tirofortuna.entities.Game;
import com.tirofortuna.service.IGameService;

public class GameControllerTest {

    @Mock
    private IGameService gameServiceMock;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Mock data
        Game game1 = new Game(1L, "Game 1", null);
        Game game2 = new Game(2L, "Game 2", null);
        List<Game> games = Arrays.asList(game1, game2);
        when(gameServiceMock.findAll()).thenReturn(games);


        // Act
        ResponseEntity<?> response = gameController.findAll();
        List<GameDTO> gameDTOS = (List<GameDTO>) response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(gameDTOS);
        assertEquals(2, gameDTOS.size());
        verify(gameServiceMock, times(1)).findAll();

        // Verify each game DTO
        for (int i = 0; i < games.size(); i++) {
            GameDTO gameDTO = gameDTOS.get(i);
            Game game = games.get(i);

            assertEquals(game.getId(), gameDTO.getId());
            assertEquals(game.getName(), gameDTO.getName());
            assertEquals(game.getDrawList(), gameDTO.getDrawList());
        }
    }

    @Test
    public void testFindById() {
        // Mock data
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        when(gameServiceMock.findById(gameId)).thenReturn(Optional.of(game));

        // Act
        ResponseEntity<?> response = gameController.findById(gameId);
        GameDTO gameDTO = ((GameDTO) response.getBody());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(gameDTO);
        assertEquals(game.getId(), gameDTO.getId());
        assertEquals(game.getName(), gameDTO.getName());
        verify(gameServiceMock, times(1)).findById(gameId);
    }

    @Test
    void testFindById_GameNotFound() {
        // Mock data
        Long gameId = 1L;

        when(gameServiceMock.findById(gameId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = gameController.findById(gameId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Game not found", response.getBody());
        verify(gameServiceMock, times(1)).findById(gameId);
    }

    @Test
    public void testSave() throws URISyntaxException {
        // Create a mock GameDTO
        GameDTO gameDTO = new GameDTO();
        gameDTO.setName("New Game");

        // Call the save method in the GameController
        ResponseEntity<?> responseEntity = gameController.save(gameDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(new URI("/api/v1/game/"), responseEntity.getHeaders().getLocation());
        verify(gameServiceMock, times(1)).save(any(Game.class));
    }

    @Test
    void testSave_InvalidName() {
        // Mock data
        GameDTO gameDTO = new GameDTO(null, " ", null);

        // Act
        ResponseEntity<?> response = gameController.save(gameDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Name is required", response.getBody());
        verify(gameServiceMock, never()).save(any(Game.class));
    }

    @Test
    void testUpdateGameById() {
        // Mock data
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        GameDTO gameDTO = new GameDTO(null, "Game 1 Updated", null);
        when(gameServiceMock.findById(gameId)).thenReturn(Optional.of(game));

        // Act
        ResponseEntity<?> response = gameController.updateGameById(gameId, gameDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Game updated", response.getBody());
        assertEquals(gameId, game.getId());
        assertEquals(gameDTO.getName(), game.getName());
        verify(gameServiceMock, times(1)).save(game);
    }

    @Test
    void testUpdateGameById_GameNotFound() {
        // Mock data
        Long gameId = 1L;
        GameDTO gameDTO = new GameDTO(null, "Updated Game", null);
        when(gameServiceMock.findById(gameId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = gameController.updateGameById(gameId, gameDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Game not found", response.getBody());
        verify(gameServiceMock, never()).save(any(Game.class));
    }

    @Test
    void testUpdateGameById_InvalidName() {
        // Mock data
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        GameDTO gameDTO = new GameDTO(null, "", null);

        when(gameServiceMock.findById(gameId)).thenReturn(Optional.of(game));

        // Act
        ResponseEntity<?> response = gameController.updateGameById(gameId, gameDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Name is required", response.getBody());
        verify(gameServiceMock, never()).save(any(Game.class));
    }

    @Test
    void testDeleteById() {
        // Mock data
        Long gameId = 1L;

        // Act
        ResponseEntity<?> response = gameController.deleteById(gameId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Game deleted successfully", response.getBody());
        verify(gameServiceMock, times(1)).deleteById(gameId);
    }

    @Test
    void testDeleteById_InvalidId() {
        // Act
        ResponseEntity<?> response = gameController.deleteById(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id is required", response.getBody());
        verify(gameServiceMock, never()).deleteById(anyLong());
    }
}
