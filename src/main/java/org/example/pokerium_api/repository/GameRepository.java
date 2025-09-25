package org.example.pokerium_api.repository;

import org.example.pokerium_api.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}
