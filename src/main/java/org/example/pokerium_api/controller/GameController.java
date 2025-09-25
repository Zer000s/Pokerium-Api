package org.example.pokerium_api.controller;

import org.example.pokerium_api.dto.ActionDto;
import org.example.pokerium_api.dto.CreateGameDto;
import org.example.pokerium_api.exception.ApiException;
import org.example.pokerium_api.repository.GamePlayerRepository;
import org.example.pokerium_api.repository.GameRepository;
import org.example.pokerium_api.repository.UserRepository;
import org.example.pokerium_api.security.JwtUtils;
import org.example.pokerium_api.service.GameService;
import lombok.RequiredArgsConstructor;
import org.example.pokerium_api.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GamePlayerRepository gamePlayerRepository;

    public GameController(GameService gameService,
                          UserRepository userRepository,
                          GameRepository gameRepository,
                          GamePlayerRepository gamePlayerRepository)
    {
        this.gameService = gameService;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gamePlayerRepository = gamePlayerRepository;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateGameDto dto, Principal principal) {
        String email = principal.getName();
        UUID userId = userRepository.findIdByEmail(email);
        boolean gameExists = gameRepository.existsByOwnerId(userId);
        if(gameExists){
            throw new ApiException("Game already exists", 409);
        }
        return ResponseEntity.ok(gameService.createGame(userId, dto));
    }

    @PostMapping("/{gameId}/join")
    public ResponseEntity<?> join(@PathVariable Long gameId, Principal principal) {
        String email = principal.getName();
        UUID userId = userRepository.findIdByEmail(email);
        boolean gamePlayerExists = gamePlayerRepository.existsByUserId(userId);
        if(gamePlayerExists){
            throw new ApiException("Player already in game", 409);
        }
        gameService.joinGame(gameId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{gameId}/start")
    public ResponseEntity<?> start(@PathVariable Long gameId) {
        gameService.startGame(gameId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{gameId}/action")
    public ResponseEntity<?> action(@PathVariable Long gameId, Principal principal, @RequestBody ActionDto action) {
        String email = principal.getName();
        UUID userId = userRepository.findIdByEmail(email);
        gameService.playerAction(gameId, userId, action);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<?> state(@PathVariable Long gameId) {
        return ResponseEntity.ok(gameService.getState(gameId));
    }
}