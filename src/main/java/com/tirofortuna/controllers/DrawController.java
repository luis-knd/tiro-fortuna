package com.tirofortuna.controllers;

import com.tirofortuna.controllers.dto.DrawDTO;
import com.tirofortuna.controllers.dto.mapper.DrawMapper;
import com.tirofortuna.entities.Draw;
import com.tirofortuna.service.IDrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.time.DateUtils.parseDate;

@Tag(name = "Draw", description = "Draw Lottery management APIs")
@RestController
@RequestMapping("/api/v1/draw")
public class DrawController {
    private final IDrawService drawService;

    public DrawController(IDrawService drawService) {
        this.drawService = drawService;
    }

    @Operation(
        summary = "Retrieve all draws",
        description = "Get all Draws object. The response is a list of Draws object.",
        tags = {"getAll"})
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<DrawDTO> drawList = drawService.findAll()
            .stream()
            .map(DrawMapper::toDrawDTO)
            .toList();
        return ResponseEntity.ok(drawList);
    }

    @Operation(
        summary = "Retrieve a draw by Id",
        description = "Get a Draw object by specifying its id. The response is Draw object.",
        tags = {"get"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Draw found"),
        @ApiResponse(responseCode = "404", description = "Draw not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Draw> drawOptional = drawService.findById(id);
        if (drawOptional.isPresent()) {
            return ResponseEntity.ok(DrawMapper.toDrawDTO(drawOptional.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Draw not found");
    }


    @Operation(
        summary = "Save a draw",
        description = "Create a new Draw object. The request body must contain the date of the draw, the game id and" +
            " the draw result. The response is the created Draw object.",
        tags = {"create"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Draw created"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody DrawDTO drawDTO) {
        if (drawDTO.getDate() == null || drawDTO.getGame() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date and game are required");
        }

        try {
            drawService.save(DrawMapper.toDrawEntity(drawDTO));
            return ResponseEntity.created(new URI("/api/v1/draw")).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating new draw", e);
        }
    }

    @Operation(
        summary = "Update draw by ID",
        description = "Update an existing Draw object by its id. The request body must contain the new name of the draw." +
            " The response is a confirmation message.",
        tags = {"update"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Draw updated"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Draw not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDrawById(@PathVariable Long id, @RequestBody DrawDTO drawDTO) {
        Optional<Draw> drawOptional = drawService.findById(id);
        if (drawOptional.isPresent() &&
            drawDTO.getDate() != null &&
            drawDTO.getGame() != null
        ) {
            Draw draw = drawOptional.get();
            draw.setDate(drawDTO.getDate());
            draw.setGame(drawDTO.getGame());
            draw.setDrawResult(drawDTO.getDrawResult());
            drawService.save(draw);
            return ResponseEntity.ok("Draw updated");
        }
        return drawOptional.isPresent() ?
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date and game are required") :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Draw not found");
    }


    @Operation(
        summary = "Delete draw by ID",
        description = "Delete an existing Draw object by its id. The response is a confirmation message.",
        tags = {"delete"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Draw deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            drawService.deleteById(id);
            return ResponseEntity.ok("Draw deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is required");
    }

    @Operation(
        summary = "Retrieve draws within a date range",
        description = "Get draw objects between two dates.",
        tags = {"get"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Draw found"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/{minDate}/{maxDate}")
    public ResponseEntity<?> findDrawByDateInRange(@Valid @PathVariable String minDate,
                                                   @Valid @PathVariable String maxDate
    ) {
        try {
            Date parsedMinDate = parseDate(minDate, "yyyy-MM-dd");
            Date parsedMaxDate = parseDate(maxDate, "yyyy-MM-dd");

            if (parsedMinDate.after(parsedMaxDate)) {
                return ResponseEntity.badRequest().body("Invalid date range. minDate must be earlier than maxDate.");
            }

            List<DrawDTO> drawList = drawService.findDrawByDateInRange(parsedMinDate, parsedMaxDate)
                .stream()
                .map(DrawMapper::toDrawDTO)
                .toList();
            return ResponseEntity.ok(drawList);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Please provide dates in yyyy-MM-dd format.");
        }
    }
}
