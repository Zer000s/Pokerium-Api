package org.example.pokerium_api.service;

import org.example.pokerium_api.dto.ActionDto;
import org.example.pokerium_api.dto.CreateGameDto;
import org.example.pokerium_api.engine.Deck;
import org.example.pokerium_api.engine.GameEngine;
import org.example.pokerium_api.engine.GameState;
import org.example.pokerium_api.engine.PlayerState;
import org.example.pokerium_api.exception.ApiException;
import org.example.pokerium_api.model.Game;
import org.example.pokerium_api.model.GamePlayer;
import org.example.pokerium_api.repository.GamePlayerRepository;
import org.example.pokerium_api.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepo;
    private final GamePlayerRepository playerRepo;
    private final GameEngine engine;
    private final SimpMessagingTemplate ws;

    public Game createGame(UUID ownerId, CreateGameDto dto) {
        Game game = Game.builder()
                .ownerId(ownerId)
                .state("CREATED")
                .smallBlind(dto.getSmallBlind())
                .bigBlind(dto.getBigBlind())
                .pot(0)
                .createdAt(Instant.now())
                .dealerIndex(0)
                .build();
        gameRepo.save(game);

        GamePlayer player = GamePlayer.builder()
                .gameId(game.getId())
                .userId(ownerId)
                .stack(1000)
                .status("ACTIVE")
                .seatIndex(playerRepo.findByGameId(game.getId()).size())
                .build();
        playerRepo.save(player);

        return game;
    }

    public void joinGame(Long gameId, UUID userId) {
        GamePlayer player = GamePlayer.builder()
                .gameId(gameId)
                .userId(userId)
                .stack(1000)
                .status("ACTIVE")
                .seatIndex(playerRepo.findByGameId(gameId).size())
                .build();
        playerRepo.save(player);
    }

    public void startGame(Long gameId) {
        List<GamePlayer> players = playerRepo.findByGameId(gameId);
        Deck deck = new Deck();
        List<PlayerState> ps = new ArrayList<>();

        for (GamePlayer p : players) {
            p.setCard1(deck.draw());
            p.setCard2(deck.draw());
            playerRepo.save(p);

            ps.add(PlayerState.builder()
                    .userId(p.getUserId())
                    .status("ACTIVE")
                    .stack(p.getStack())
                    .card1(p.getCard1())
                    .card2(p.getCard2())
                    .currentBet(0)
                    .build());
        }

        GameState state = GameState.builder()
                .gameId(gameId)
                .players(ps)
                .communityCards(new ArrayList<>())
                .phase("PREFLOP")
                .pot(0)
                .currentPlayerIndex(0)
                .deck(deck)
                .build();

        Game game = gameRepo.getById(gameId);
        game.setState("RUNNING");

        engine.save(gameId, state);
        broadcast(gameId);
    }

    public void playerAction(Long gameId, UUID userId, ActionDto action) {
        GameState state = engine.get(gameId);
        // MVP: просто логируем и переходим к следующему игроку
        System.out.println("Player " + userId + " action: " + action.getAction());
        state.setCurrentPlayerIndex((state.getCurrentPlayerIndex() + 1) % state.getPlayers().size());
        engine.save(gameId, state);
        broadcast(gameId);
    }

    public Map<String, Object> getState(Long gameId) {
        Map<String, Object> map = new HashMap<>();
        map.put("game", gameRepo.findById(gameId).orElseThrow());
        map.put("players", playerRepo.findByGameId(gameId));
        map.put("state", engine.get(gameId));
        return map;
    }

    private void broadcast(Long gameId) {
        ws.convertAndSend("/topic/game." + gameId, engine.get(gameId));
    }
}