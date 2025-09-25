package org.example.pokerium_api.service;

import org.example.pokerium_api.dto.ActionDto;
import org.example.pokerium_api.dto.CreateGameDto;
import org.example.pokerium_api.model.Game;
import org.example.pokerium_api.model.GamePlayer;
import org.example.pokerium_api.repository.GamePlayerRepository;
import org.example.pokerium_api.repository.GameRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {
    private final GameRepository gameRepo;
    private final GamePlayerRepository playerRepo;

    public GameService(GameRepository gameRepo, GamePlayerRepository playerRepo) {
        this.gameRepo = gameRepo;
        this.playerRepo = playerRepo;
    }

    public Game createGame(Long ownerId, CreateGameDto dto) {
        Game game = new Game();
        game.setState("CREATED");
        game.setSmallBlind(dto.getSmallBlind());
        game.setBigBlind(dto.getBigBlind());
        game.setPot(0);
        game.setCreatedAt(Instant.now());
        return gameRepo.save(game);
    }

    public void joinGame(Long gameId, Long userId) {
        GamePlayer player = new GamePlayer();
        player.setGameId(gameId);
        player.setUserId(userId);
        player.setStack(1000); // стартовый баланс
        player.setStatus("ACTIVE");
        playerRepo.save(player);
    }

    public void playerAction(Long gameId, Long userId, ActionDto action) {
        // MVP: просто логируем действия, потом добавим механику
        System.out.printf("Player %d in game %d: %s %d%n", userId, gameId, action.getAction(), action.getAmount());
    }

    public Map<String, Object> getState(Long gameId) {
        Game game = gameRepo.findById(gameId).orElseThrow();
        List<GamePlayer> players = playerRepo.findByGameId(gameId);

        Map<String, Object> state = new HashMap<>();
        state.put("game", game);
        state.put("players", players);
        return state;
    }
}