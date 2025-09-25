package org.example.pokerium_api.engine;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameEngine {
    private final Map<Long, GameState> games = new ConcurrentHashMap<>();

    public GameState get(Long gameId) { return games.get(gameId); }
    public void save(Long gameId, GameState state) { games.put(gameId, state); }
}
