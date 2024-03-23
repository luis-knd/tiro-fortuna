package com.tirofortuna.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "draw")
public class Draw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Draw date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotBlank(message = "Game is required")
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @JsonIgnore
    private Game game;

    @OneToOne(mappedBy = "draw_result", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private DrawResult drawResult;
}
