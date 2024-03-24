package com.tirofortuna.controllers;

import com.tirofortuna.controllers.dto.DrawResultDTO;
import com.tirofortuna.entities.Draw;
import com.tirofortuna.entities.DrawResult;
import com.tirofortuna.entities.Game;
import com.tirofortuna.service.IDrawResultService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DrawResultControllerTest {

    @Mock
    private IDrawResultService drawResultServiceMock;

    @InjectMocks
    private DrawResultController drawResultController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Mock Data
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Draw draw = new Draw(null, new Date(), game, null);
        Draw draw2 = new Draw(null, new Date(), game, null);

        DrawResult drawResult1 = new DrawResult(1L, draw, "Result 1");
        DrawResult drawResult2 = new DrawResult(2L, draw2, "Result 2");
        List<DrawResult> drawResults = Arrays.asList(drawResult1, drawResult2);
        when(drawResultServiceMock.findAll()).thenReturn(drawResults);

        // Act
        ResponseEntity<?> response = drawResultController.findAll();
        List<DrawResultDTO> drawResultDTOS = (List<DrawResultDTO>) response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(drawResultDTOS);
        assertEquals(2, drawResultDTOS.size());
        verify(drawResultServiceMock, times(1)).findAll();

        // Verify each draw result DTO
        for (int i = 0; i < drawResultDTOS.size(); i++) {
            assertEquals(drawResults.get(i).getId(), drawResultDTOS.get(i).getId());
            assertEquals(drawResults.get(i).getDraw_result().getId(), drawResultDTOS.get(i).getDraw_result().getId());
            assertEquals(drawResults.get(i).getResult(), drawResultDTOS.get(i).getResult());
        }
    }


    @Test
    void testFindById() {
        // Mock Data
        Long drawResultId = 1L;
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Draw draw = new Draw(null, new Date(), game, null);
        DrawResult drawResult = new DrawResult(drawResultId, draw, "Result 1");
        when(drawResultServiceMock.findById(drawResultId)).thenReturn(Optional.of(drawResult));

        // Act
        ResponseEntity<?> response = drawResultController.findById(drawResultId);
        DrawResultDTO drawResultDTO = (DrawResultDTO) response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(drawResultDTO);
        assertEquals(drawResult.getId(), drawResultDTO.getId());
        assertEquals(drawResult.getDraw_result(), drawResultDTO.getDraw_result());
        assertEquals(drawResult.getResult(), drawResultDTO.getResult());
        verify(drawResultServiceMock, times(1)).findById(drawResultId);
    }

    @Test
    void testFindById_DrawResultNotFound() {
        // Mock Data
        Long drawResultId = 1L;
        when(drawResultServiceMock.findById(drawResultId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = drawResultController.findById(drawResultId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Draw Result not found", response.getBody());
        verify(drawResultServiceMock, times(1)).findById(drawResultId);
    }

    @Test
    void testSave() throws URISyntaxException {
        // Mock Data
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Draw draw = new Draw(null, new Date(), game, null);
        DrawResultDTO drawResultDTO = new DrawResultDTO(null, draw, "Result 1");

        // Act
        ResponseEntity<?> response = drawResultController.save(drawResultDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new URI("/api/v1/draw-result"), response.getHeaders().getLocation());
        verify(drawResultServiceMock, times(1)).save(any(DrawResult.class));
    }

    @Test
    void testSave_InvalidRequest() {
        // Mock Data
        DrawResultDTO drawResultDTO = new DrawResultDTO(null, null, null);

        // Act
        ResponseEntity<?> response = drawResultController.save(drawResultDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Draw, and result are required", response.getBody());
        verify(drawResultServiceMock, never()).save(any(DrawResult.class));
    }

    @Test
    void testUpdateDrawById() {
        // Mock Data
        Long drawResultId = 1L;
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Draw draw = new Draw(null, new Date(), game, null);
        DrawResult drawResult = new DrawResult(drawResultId, draw, "Result 1");
        DrawResultDTO drawResultDTO = new DrawResultDTO(null, draw, "Result 1 Updated");
        when(drawResultServiceMock.findById(drawResultId)).thenReturn(Optional.of(drawResult));

        // Act
        ResponseEntity<?> response = drawResultController.updateDrawById(drawResultId, drawResultDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Draw Result updated", response.getBody());
        assertEquals(drawResultDTO.getDraw_result(), drawResult.getDraw_result());
        assertEquals(drawResultDTO.getResult(), drawResult.getResult());
        verify(drawResultServiceMock, times(1)).save(drawResult);
    }

    @Test
    void testUpdateDrawById_DrawResultNotFound() {
        // Mock Data
        Long drawResultId = 1L;
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Draw draw = new Draw(null, new Date(), game, null);
        DrawResultDTO drawResultDTO = new DrawResultDTO(null, draw, "Result 1");
        when(drawResultServiceMock.findById(drawResultId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = drawResultController.updateDrawById(drawResultId, drawResultDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Draw Result not found", response.getBody());
        verify(drawResultServiceMock, never()).save(any(DrawResult.class));
    }

    @Test
    void testUpdateDrawById_InvalidRequest() {
        // Mock Data
        Long drawResultId = 1L;
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Draw draw = new Draw(null, new Date(), game, null);
        DrawResult drawResult = new DrawResult(drawResultId, draw, "Result 1");
        DrawResultDTO drawResultDTO = new DrawResultDTO(null, null, null);
        when(drawResultServiceMock.findById(drawResultId)).thenReturn(Optional.of(drawResult));

        // Act
        ResponseEntity<?> response = drawResultController.updateDrawById(drawResultId, drawResultDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Draw and result are required", response.getBody());
        verify(drawResultServiceMock, never()).save(any(DrawResult.class));
    }

    @Test
    void testDeleteById() {
        // Mock Data
        Long drawResultId = 1L;

        // Act
        ResponseEntity<?> response = drawResultController.deleteById(drawResultId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Draw result deleted successfully", response.getBody());
        verify(drawResultServiceMock, times(1)).deleteById(drawResultId);
    }

    @Test
    void testDeleteById_InvalidId() {
        // Act
        ResponseEntity<?> drawResultIdNullResponse = drawResultController.deleteById(null);
        ResponseEntity<?> drawResultIdInvalidResponse = drawResultController.deleteById(-1L);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, drawResultIdNullResponse.getStatusCode());
        assertEquals("Id is required", drawResultIdNullResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, drawResultIdInvalidResponse.getStatusCode());
        assertEquals("Id must be greater than 0", drawResultIdInvalidResponse.getBody());
        verify(drawResultServiceMock, never()).deleteById(anyLong());
    }
}
