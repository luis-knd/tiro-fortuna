package com.tirofortuna.controllers;

import com.tirofortuna.controllers.dto.DrawDTO;
import com.tirofortuna.entities.Draw;
import com.tirofortuna.entities.Game;
import com.tirofortuna.service.IDrawService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DrawControllerTest {

    @Mock
    private IDrawService drawServiceMock;

    @InjectMocks
    private DrawController drawController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Mock Data
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Date currentDate = Date.from(LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant());
        Date dateOneMonthBefore = Date.from(LocalDate.now().minusMonths(1)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant());
        Draw draw1 = new Draw(1L, currentDate, game, null);
        Draw draw2 = new Draw(2L, dateOneMonthBefore, game, null);
        List<Draw> draws = Arrays.asList(draw1, draw2);
        when(drawServiceMock.findAll()).thenReturn(draws);

        // Act
        ResponseEntity<?> response = drawController.findAll();
        List<DrawDTO> drawDTOS = (List<DrawDTO>) response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(drawDTOS);
        assertEquals(2, drawDTOS.size());
        verify(drawServiceMock, times(1)).findAll();

        // Verify each draw DTO
        for (int i = 0; i < draws.size(); i++) {
            DrawDTO drawDTO = drawDTOS.get(i);
            Draw draw = draws.get(i);

            assertEquals(drawDTO.getId(), draw.getId());
            assertEquals(drawDTO.getDate(), draw.getDate());
            assertEquals(drawDTO.getGame().getId(), draw.getGame().getId());
            assertEquals(drawDTO.getGame().getName(), draw.getGame().getName());
            assertEquals(drawDTO.getDrawResult(), draw.getDrawResult());
        }
    }

    @Test
    void testFindById() {
        // Mock Data
        Long drawId = 1L;
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Draw draw = new Draw(drawId, new Date(), game, null);
        when(drawServiceMock.findById(drawId)).thenReturn(Optional.of(draw));

        // Act
        ResponseEntity<?> response = drawController.findById(drawId);
        DrawDTO drawDTO = (DrawDTO) response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(drawDTO);
        assertEquals(draw.getId(), drawDTO.getId());
        assertEquals(draw.getDate(), drawDTO.getDate());
        assertEquals(draw.getGame(), drawDTO.getGame());
        assertEquals(draw.getDrawResult(), drawDTO.getDrawResult());
        verify(drawServiceMock, times(1)).findById(drawId);
    }

    @Test
    void testFindById_DrawNotFound() {
        // Mock Data
        Long drawId = 1L;

        when(drawServiceMock.findById(drawId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = drawController.findById(drawId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Draw not found", response.getBody());
        verify(drawServiceMock, times(1)).findById(drawId);
    }

    @Test
    void testSave() throws URISyntaxException {
        // Mock Data
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        DrawDTO drawDTO = new DrawDTO(null, new Date(), game, null);

        // Act
        ResponseEntity<?> response = drawController.save(drawDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new URI("/api/v1/draw"), response.getHeaders().getLocation());
        verify(drawServiceMock, times(1)).save(any(Draw.class));
    }

    @Test
    void testSave_InvalidRequest() {
        // Mock Data
        DrawDTO drawDTO = new DrawDTO(null, null, null, null);

        // Act
        ResponseEntity<?> response = drawController.save(drawDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Date and game are required", response.getBody());
        verify(drawServiceMock, never()).save(any(Draw.class));
    }

    @Test
    void testUpdateDrawById() {
        // Mock Data
        Long drawId = 1L;
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Draw draw = new Draw(drawId, new Date(), game, null);
        DrawDTO drawDTO = new DrawDTO(drawId, new Date(), game, null);
        when(drawServiceMock.findById(drawId)).thenReturn(Optional.of(draw));

        // Act
        ResponseEntity<?> response = drawController.updateDrawById(drawId, drawDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Draw updated", response.getBody());
        assertEquals(drawDTO.getId(), draw.getId());
        assertEquals(drawDTO.getDate(), draw.getDate());
        assertEquals(drawDTO.getGame(), draw.getGame());
        assertEquals(drawDTO.getDrawResult(), draw.getDrawResult());
        verify(drawServiceMock, times(1)).save(draw);
    }

    @Test
    void testUpdateDrawById_DrawNotFound() {
        // Mock Data
        Long drawId = 1L;
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        DrawDTO drawDTO = new DrawDTO(null, new Date(), game, null);
        when(drawServiceMock.findById(drawId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = drawController.updateDrawById(drawId, drawDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Draw not found", response.getBody());
        verify(drawServiceMock, never()).save(any(Draw.class));
    }

    @Test
    void testUpdateDrawById_InvalidRequest() {
        // Mock Data
        Long drawId = 1L;
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        DrawDTO drawDTO = new DrawDTO(null, null, null, null);
        Draw draw = new Draw(drawId, new Date(), game, null);
        when(drawServiceMock.findById(drawId)).thenReturn(Optional.of(draw));

        // Act
        ResponseEntity<?> response = drawController.updateDrawById(drawId, drawDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Date and game are required", response.getBody());
        verify(drawServiceMock, never()).save(any(Draw.class));
    }

    @Test
    void testDeleteById() {
        // Mock Data
        Long drawId = 1L;

        // Act
        ResponseEntity<?> response = drawController.deleteById(drawId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Draw deleted successfully", response.getBody());
        verify(drawServiceMock, times(1)).deleteById(drawId);
    }

    @Test
    void testDeleteById_InvalidId() {
        // Act
        ResponseEntity<?> response = drawController.deleteById(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Id is required", response.getBody());
        verify(drawServiceMock, never()).deleteById(anyLong());
    }

    @Test
    void testFindDrawByDateInRange() throws ParseException {
        // Mock Data
        String minDate = "2022-01-01";
        String maxDate = "2022-01-31";
        Long gameId = 1L;
        Game game = new Game(gameId, "Game 1", null);
        Date parsedMinDate = new SimpleDateFormat("yyyy-MM-dd").parse(minDate);
        Date parsedMaxDate = new SimpleDateFormat("yyyy-MM-dd").parse(maxDate);
        Draw draw1 = new Draw(1L, parsedMinDate, game, null);
        Draw draw2 = new Draw(2L, parsedMaxDate, game, null);
        List<Draw> draws = Arrays.asList(draw1, draw2);
        when(drawServiceMock.findDrawByDateInRange(parsedMinDate, parsedMaxDate)).thenReturn(draws);

        // Act
        ResponseEntity<?> response = drawController.findDrawByDateInRange(minDate, maxDate);
        List<DrawDTO> drawDTOS = (List<DrawDTO>) response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(drawDTOS);
        assertEquals(2, drawDTOS.size());
        verify(drawServiceMock, times(1))
            .findDrawByDateInRange(parsedMinDate, parsedMaxDate);

        // Verify each draw DTO
        for (int i = 0; i < draws.size(); i++) {
            DrawDTO drawDTO = drawDTOS.get(i);
            Draw draw = draws.get(i);

            assertEquals(drawDTO.getId(), draw.getId());
            assertEquals(drawDTO.getDate(), draw.getDate());
            assertEquals(drawDTO.getGame().getId(), draw.getGame().getId());
            assertEquals(drawDTO.getGame().getName(), draw.getGame().getName());
            assertEquals(drawDTO.getDrawResult(), draw.getDrawResult());
        }
    }

    @Test
    void testFindDrawByDateInRange_InvalidDateRange() {
        // Mock Data
        String minDate = "2022-01-31";
        String maxDate = "2022-01-01";

        // Act
        ResponseEntity<?> response = drawController.findDrawByDateInRange(minDate, maxDate);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid date range. minDate must be earlier than maxDate.", response.getBody());
        verify(drawServiceMock, never()).findDrawByDateInRange(any(Date.class), any(Date.class));
    }

    @Test
    void testFindDrawByDateInRange_InvalidDateFormat() {
        // Mock Data
        String minDate = "2022/01/01";
        String maxDate = "2022/01/31";

        // Act
        ResponseEntity<?> response = drawController.findDrawByDateInRange(minDate, maxDate);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid date format. Please provide dates in yyyy-MM-dd format.", response.getBody());
        verify(drawServiceMock, never()).findDrawByDateInRange(any(Date.class), any(Date.class));
    }
}
