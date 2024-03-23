package com.tirofortuna.controllers.dto;

import com.tirofortuna.entities.DrawResult;
import com.tirofortuna.entities.Game;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrawDTO {

    private Long id;

    @NotNull(message = "Draw date is required")
    @PastOrPresent(message = "Draw date must be in the past or present")
    private Date date;

    @NotNull(message = "Game is required")
    @Positive(message = "Game id must be greater than 0")
    private Game game;

    private DrawResult drawResult;
}
