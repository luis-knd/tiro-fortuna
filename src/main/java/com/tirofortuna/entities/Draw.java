package com.tirofortuna.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    private Date date;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @JsonIgnore
    private Game game;

    @OneToOne(mappedBy = "draw_result", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private DrawResult drawResult;
}
