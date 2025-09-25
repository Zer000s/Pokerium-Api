package org.example.pokerium_api.engine;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerState {
    private UUID userId;
    private String status;
    private Integer stack;
    private String card1;
    private String card2;
    private Integer currentBet;
}
