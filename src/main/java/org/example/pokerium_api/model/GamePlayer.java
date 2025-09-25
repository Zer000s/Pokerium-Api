package org.example.pokerium_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "game_players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GamePlayer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gameId;
    private Long userId;
    private Integer stack;
    private String status; // ACTIVE, FOLDED, ALL_IN
}