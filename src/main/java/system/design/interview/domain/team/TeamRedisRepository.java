package system.design.interview.domain.team;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamRedisRepository {

    private final RedisTemplate<String, Team> redisTemplate;
    public void save(Long id, Team team) {
        redisTemplate.opsForValue().set(String.valueOf(id), team);
    }

    public void deleteById(Long id) {
        redisTemplate.opsForValue().getAndDelete(String.valueOf(id));
    }

    public Optional<Team> findById(Long teamId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(String.valueOf(teamId)));
    }
}
