package org.example.pokerium_api.engine;

import lombok.*;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameState {
    private Long gameId;
    private List<PlayerState> players;
    private List<String> communityCards;
    private String phase;
    private Integer pot;
    private Integer currentPlayerIndex;
    private Deck deck;
}