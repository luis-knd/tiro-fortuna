package com.tirofortuna.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumn(name = "draw_id", referencedColumnName = "id")
    @JsonIgnore
    private Draw draw_result;

    @Column(columnDefinition = "json")
    private String result;
}
