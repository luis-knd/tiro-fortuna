package com.tirofortuna.controllers;

import com.tirofortuna.controllers.dto.DrawResultDTO;
import com.tirofortuna.entities.DrawResult;
import com.tirofortuna.service.IDrawResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Tag(name = "DrawResult", description = "Draw Result Lottery management APIs")
@RestController
@RequestMapping("/api/v1/draw-result")
public class DrawResultController {

    private final IDrawResultService drawResultService;

    public DrawResultController(IDrawResultService drawResultService) {
        this.drawResultService = drawResultService;
    }

    @Operation(
        summary = "Retrieve all draws results",
        description = "Get all Draw results object. The response is a list of Draw results object.",
        tags = {"getAll"})
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<DrawResultDTO> drawResultList = drawResultService.findAll()
            .stream()
            .map(drawResult -> DrawResultDTO.builder()
                .id(drawResult.getId())
                .draw_result(drawResult.getDraw_result())
                .result(drawResult.getResult())
                .build()
            ).toList();
        return ResponseEntity.ok(drawResultList);
    }

    @Operation(
        summary = "Retrieve a draw result by Id",
        description = "Get a Draw Result object by specifying its id. The response is Draw Result object.",
        tags = {"get"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Draw Result found"),
        @ApiResponse(responseCode = "404", description = "Draw Result not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<DrawResult> drawResultOptional = drawResultService.findById(id);
        if (drawResultOptional.isPresent()) {
            return ResponseEntity.ok(DrawResultDTO.builder()
                .id(drawResultOptional.get().getId())
                .draw_result(drawResultOptional.get().getDraw_result())
                .result(drawResultOptional.get().getResult())
                .build()
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Draw Result not found");
    }

    @Operation(
        summary = "Save a draw result",
        description = "Create a new Draw Result object. The response is the created Draw Result object.",
        tags = {"create"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Draw Result created"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody DrawResultDTO drawResultDTO) {
        if (drawResultDTO.getDraw_result() == null || drawResultDTO.getResult() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Draw, and result is required");
        }
        try {
            drawResultService.save(
                DrawResult.builder()
                    .draw_result(drawResultDTO.getDraw_result())
                    .result(drawResultDTO.getResult())
                    .build()
            );
            return ResponseEntity.created(new URI("/api/v1/draw-result")).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating new draw result", e);
        }
    }

    @Operation(
        summary = "Update draw result by ID",
        description = "Update an existing Draw Result object by its id. The response is a confirmation message.",
        tags = {"update"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Draw Result updated"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Draw Result not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDrawById(@PathVariable Long id, @RequestBody DrawResultDTO drawResultDTO) {
        Optional<DrawResult> drawResultOptional = drawResultService.findById(id);
        if (drawResultOptional.isPresent() &&
            drawResultDTO.getDraw_result() != null &&
            drawResultDTO.getResult() != null
        ) {
            DrawResult drawResult = drawResultOptional.get();
            drawResult.setDraw_result(drawResultDTO.getDraw_result());
            drawResult.setResult(drawResultDTO.getResult());
            drawResultService.save(drawResult);
            return ResponseEntity.ok("Draw Result updated");
        }
        return drawResultOptional.isPresent() ?
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Draw and result are required") :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Draw Result not found");
    }


    @Operation(
        summary = "Delete draw result by ID",
        description = "Delete an existing Draw Result object by its id. The response is a confirmation message.",
        tags = {"delete"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Draw result deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            drawResultService.deleteById(id);
            return ResponseEntity.ok("Draw result deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is required");
    }

}
