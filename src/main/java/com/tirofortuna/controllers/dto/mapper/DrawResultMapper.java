package com.tirofortuna.controllers.dto.mapper;

import com.tirofortuna.controllers.dto.DrawResultDTO;
import com.tirofortuna.entities.DrawResult;
import org.springframework.stereotype.Component;

@Component
public class DrawResultMapper {
    public static DrawResultDTO toDrawResultDTO(DrawResult drawResult) {
        return DrawResultDTO.builder()
            .id(drawResult.getId())
            .draw_result(drawResult.getDraw_result())
            .result(drawResult.getResult())
            .build();
    }

    public static DrawResult toDrawResultEntity(DrawResultDTO drawResultDTO) {
        return DrawResult.builder()
            .id(drawResultDTO.getId())
            .draw_result(drawResultDTO.getDraw_result())
            .result(drawResultDTO.getResult())
            .build();
    }
}
