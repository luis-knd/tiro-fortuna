package com.tirofortuna.controllers.dto;

import com.tirofortuna.entities.Draw;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrawResultDTO {

    private Long id;

    @NotNull(message = "Draw is required")
    @Positive(message = "Draw id must be greater than 0")
    private Draw draw_result;

    @NotBlank(message = "Result is required")
    private String result;
}
