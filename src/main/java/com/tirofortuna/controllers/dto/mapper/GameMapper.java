package com.tirofortuna.controllers.dto.mapper;

import com.tirofortuna.controllers.dto.GameDTO;
import com.tirofortuna.entities.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    public static GameDTO toGameDTO(Game game) {
        return GameDTO.builder()
            .id(game.getId())
            .name(game.getName())
            .drawList(game.getDrawList())
            .build();
    }

    public static Game toGameEntity(GameDTO gameDTO) {
        return Game.builder()
            .id(gameDTO.getId())
            .name(gameDTO.getName())
            .drawList(gameDTO.getDrawList())
            .build();
    }
}
