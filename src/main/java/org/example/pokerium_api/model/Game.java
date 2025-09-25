package org.example.pokerium_api.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "games")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID ownerId;
    private String state; // CREATED, RUNNING, FINISHED
    private Integer smallBlind;
    private Integer bigBlind;
    private Integer pot;
    private Instant createdAt;
    private Integer dealerIndex;
}