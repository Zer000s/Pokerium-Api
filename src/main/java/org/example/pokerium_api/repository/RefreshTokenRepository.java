package org.example.pokerium_api.repository;

import org.example.pokerium_api.model.RefreshToken;
import org.example.pokerium_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    void deleteAllByUser(User user);
}