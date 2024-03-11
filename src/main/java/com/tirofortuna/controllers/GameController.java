package com.tirofortuna.controllers;

import com.tirofortuna.controllers.dto.GameDTO;
import com.tirofortuna.entities.Game;
import com.tirofortuna.service.IGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/game")
public class GameController {

    private final IGameService gameService;

    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }


    @GetMapping("/")
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

    @PostMapping("/")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            gameService.deleteById(id);
            return ResponseEntity.ok("Game deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is required");
    }
}
