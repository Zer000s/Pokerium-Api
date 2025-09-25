package org.example.pokerium_api.repository;

import org.example.pokerium_api.model.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, Long> {
    List<GamePlayer> findByGameId(Long gameId);
    boolean existsByUserId(UUID userId);
}