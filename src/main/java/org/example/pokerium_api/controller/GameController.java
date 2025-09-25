package org.example.pokerium_api.controller;

import org.example.pokerium_api.dto.ActionDto;
import org.example.pokerium_api.dto.CreateGameDto;
import org.example.pokerium_api.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateGameDto dto, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(gameService.createGame(userId, dto));
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<?> join(@PathVariable Long id, Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        gameService.joinGame(id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/action")
    public ResponseEntity<?> action(@PathVariable Long id, Principal principal, @RequestBody ActionDto action) {
        Long userId = Long.parseLong(principal.getName());
        gameService.playerAction(id, userId, action);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> state(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getState(id));
    }
}