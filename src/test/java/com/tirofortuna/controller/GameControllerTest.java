package com.tirofortuna.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tirofortuna.controllers.GameController;
import com.tirofortuna.service.IGameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IGameService gameService;

    @Test
    void testFindAll() throws Exception {
        // Mock the service response
        when(gameService.findAll()).thenReturn(Collections.emptyList());

        // Perform GET request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/game")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
