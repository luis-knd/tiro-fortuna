package com.tirofortuna.controllers.dto;

import com.tirofortuna.entities.Draw;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameDTO {
    private Long id;
    private String name;
    private List<Draw> drawList = new ArrayList<>();
}
