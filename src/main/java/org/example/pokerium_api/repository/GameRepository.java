package org.example.pokerium_api.repository;

import org.example.pokerium_api.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game getById(Long gameId);

    boolean existsByOwnerId(UUID userId);
}