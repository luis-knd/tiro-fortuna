package com.tirofortuna.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "draw_result")
public class DrawResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Draw is required")
    @OneToOne
    @JoinColumn(name = "draw_id", referencedColumnName = "id")
    @JsonIgnore
    private Draw draw_result;

    @NotBlank(message = "Result is required")
    @Column(columnDefinition = "json")
    private String result;
}
