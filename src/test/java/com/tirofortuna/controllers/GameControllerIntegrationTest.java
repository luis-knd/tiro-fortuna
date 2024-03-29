package com.tirofortuna.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tirofortuna.controllers.dto.GameDTO;
import com.tirofortuna.entities.Game;
import com.tirofortuna.service.IGameService;
import factories.GameFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(GameController.class)
@AutoConfigureMockMvc
public class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IGameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll_ReturnsListOfGames() throws Exception {
        List<Game> games = GameFactory.createFakeGameList(2);
        when(gameService.findAll()).thenReturn(games);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(games.get(0).getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(games.get(0).getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(games.get(1).getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(games.get(1).getName()));
    }

    @Test
    void findById_WithValidId_ReturnsGame() throws Exception {
        Game game = GameFactory.createFakeGame();
        when(gameService.findById(game.getId())).thenReturn(Optional.of(game));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/" + game.getId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(game.getName()));
    }

    @Test
    void findById_WithInvalidId_ReturnsNotFound() throws Exception {
        Game game = GameFactory.createFakeGame();
        when(gameService.findById(game.getId())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game/" + game.getId()))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().string("Game not found"));
    }

    @Test
    void save_ValidInput_ReturnsCreatedStatus() throws Exception {
        GameDTO gameDTO = GameFactory.createFakeGameDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(gameService).save(any(Game.class));
    }
    @Test
    void save_NameRequired_ReturnsBadRequest() throws Exception {
        GameDTO gameDTO = new GameDTO(null, "", null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/game")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().string("Name is required"));
    }

    @Test
    void updateGameById_WithValidIdAndRequestBody_ReturnsOkStatus() throws Exception {
        GameDTO gameDTO = GameFactory.createFakeGameDTO();
        when(gameService.findById(1L)).thenReturn(Optional.of(GameFactory.createFakeGame()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/game/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(gameDTO)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Game updated"));

        verify(gameService).save(any(Game.class));
    }

    @Test
    void deleteById_WithValidId_ReturnsOkStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/game/1"))
            .andExpect(MockMvcResultMatchers.status().isOk());

        verify(gameService).deleteById(1L);
    }

}
