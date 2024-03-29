package com.tirofortuna.controllers.dto;

import com.tirofortuna.entities.Draw;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "Id cannot be null")
    @Positive(message = "Id must be greater than 0")
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private List<Draw> drawList = new ArrayList<>();
}
