package org.example.pokerium_api.model;

import lombok.*;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "game_players")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GamePlayer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gameId;
    private UUID userId;
    private Integer stack;
    private String status; // ACTIVE, FOLDED
    private String card1;
    private String card2;
    private Integer seatIndex;
}