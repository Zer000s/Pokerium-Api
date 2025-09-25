package org.example.pokerium_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String state; // CREATED, RUNNING, FINISHED
    private Integer smallBlind;
    private Integer bigBlind;
    private Integer pot;
    private Instant createdAt;
}