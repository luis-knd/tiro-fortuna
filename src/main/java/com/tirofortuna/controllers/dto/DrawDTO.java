package com.tirofortuna.controllers.dto;

import com.tirofortuna.entities.DrawResult;
import com.tirofortuna.entities.Game;
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
    private Long Id;
    private Date date;
    private Double prize;
    private Game game;
    private DrawResult drawResult;
}
