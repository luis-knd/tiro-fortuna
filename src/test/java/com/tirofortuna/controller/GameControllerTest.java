package com.tirofortuna.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tirofortuna.controllers.GameController;
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
        List<Game> games = List.of(new Game(1L, "Game 1", null), new Game(2L, "Game 2", null));
        when(gameServiceMock.findAll()).thenReturn(games);

        // Call the controller method
        ResponseEntity<List<GameDTO>> responseEntity = (ResponseEntity<List<GameDTO>>) gameController.findAll();

        // Assertions
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        List<GameDTO> gamesDto = responseEntity.getBody();
        assertNotNull(gamesDto);
        assertEquals(games.size(), gamesDto.size());

        // Verify each game DTO
        for (int i = 0; i < games.size(); i++) {
            GameDTO gameDto = gamesDto.get(i);
            Game game = games.get(i);

            assertEquals(game.getId(), gameDto.getId());
            assertEquals(game.getName(), gameDto.getName());
            assertEquals(game.getDrawList(), gameDto.getDrawList());
        }
    }

    @Test
    public void testSave() {
        // Create a mock GameDTO
        GameDTO gameDTO = new GameDTO();
        gameDTO.setName("New Game");

        // Call the save method in the GameController
        ResponseEntity<?> responseEntity = gameController.save(gameDTO);

        // Verify that the save method in the GameService is called once with any Game object
        verify(gameServiceMock, times(1)).save(any(Game.class));

        // Check that the HTTP status code in the response is HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}
