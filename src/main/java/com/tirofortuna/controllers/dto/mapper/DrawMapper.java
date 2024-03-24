package com.tirofortuna.controllers.dto.mapper;

import com.tirofortuna.controllers.dto.DrawDTO;
import com.tirofortuna.entities.Draw;
import org.springframework.stereotype.Component;

@Component
public class DrawMapper {

    public static DrawDTO toDrawDTO(Draw draw) {
        return DrawDTO.builder()
            .id(draw.getId())
            .date(draw.getDate())
            .game(draw.getGame())
            .drawResult(draw.getDrawResult())
            .build();
    }

    public static Draw toDrawEntity(DrawDTO drawDTO) {
        return Draw.builder()
            .id(drawDTO.getId())
            .date(drawDTO.getDate())
            .game(drawDTO.getGame())
            .drawResult(drawDTO.getDrawResult())
            .build();
    }
}
