package com.tirofortuna.controllers;

import com.tirofortuna.controllers.dto.GameDTO;
import com.tirofortuna.entities.Game;
import com.tirofortuna.service.IGameService;
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


@Tag(name = "Game", description = "Game management APIs")
@RestController
@RequestMapping("/api/v1/game")
public class GameController {

    private final IGameService gameService;

    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }


    @Operation(
        summary = "Retrieve all games",
        description = "Get all Games object. The response is a list of Games object with id, name and draw list.",
        tags = {"getAll"})
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        List<GameDTO> gameList = gameService.findAll()
            .stream()
            .map(game -> GameDTO.builder()
                .id(game.getId())
                .name(game.getName())
                .drawList(game.getDrawList())
                .build()
            ).toList();
        return ResponseEntity.ok(gameList);
    }

    @Operation(
        summary = "Retrieve a game by Id",
        description = "Get a Game object by specifying its id. The response is Game object with id, name and draw list.",
        tags = {"get"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Game found"),
        @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Game> gameOptional = gameService.findById(id);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            GameDTO gameDTO = GameDTO.builder()
                .id(game.getId())
                .name(game.getName())
                .drawList(game.getDrawList())
                .build();
            return ResponseEntity.ok(gameDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
    }

    @Operation(
        summary = "Save a game",
        description = "Create a new Game object. The request body must contain the name of the game. The response is the created Game object with its id, name and draw list.",
        tags = {"create"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Game created"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody GameDTO gameDTO) {
        if (gameDTO.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is required");
        }

        try {
            gameService.save(
                Game.builder().name(
                    gameDTO.getName()
                ).build()
            );
            return ResponseEntity.created(new URI("/api/v1/game/")).build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error creating URI", e);
        }
    }

    @Operation(
        summary = "Update game by ID",
        description = "Update an existing Game object by its id. The request body must contain the new name of the game. The response is a confirmation message.",
        tags = {"update"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Game updated"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGameById(@PathVariable Long id, @RequestBody GameDTO gameDTO) {
        Optional<Game> gameOptional = gameService.findById(id);
        if (gameOptional.isPresent() && !gameDTO.getName().isBlank()) {
            Game game = gameOptional.get();
            game.setName(gameDTO.getName());
            gameService.save(game);
            return ResponseEntity.ok("Game updated");
        }
        return gameDTO.getName().isBlank() ?
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name is required") :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
    }

    @Operation(
        summary = "Delete game by ID",
        description = "Delete an existing Game object by its id. The response is a confirmation message.",
        tags = {"delete"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Game deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            gameService.deleteById(id);
            return ResponseEntity.ok("Game deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is required");
    }
}
