package org.example.pokerium_api.engine;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class GameEngine {

    private final RedisTemplate<String, Object> redisTemplate;

    private String key(Long gameId) {
        return "game:" + gameId;
    }

    public void save(Long gameId, GameState state) {
        redisTemplate.opsForValue().set(key(gameId), state, 2, TimeUnit.HOURS); // TTL на 2 часа
    }

    public GameState get(Long gameId) {
        return (GameState) redisTemplate.opsForValue().get(key(gameId));
    }

    public void delete(Long gameId) {
        redisTemplate.delete(key(gameId));
    }
}