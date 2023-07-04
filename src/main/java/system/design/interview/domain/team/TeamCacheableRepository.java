package system.design.interview.domain.team;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import system.design.interview.CacheableRepository;

import java.util.Optional;

@Repository
public class TeamCacheableRepository extends CacheableRepository<Team, Long> {

    private static final String PREFIX = Team.class.getName() + "_";

    public TeamCacheableRepository(TeamRepository teamRepository,
                                   RedisTemplate<String, Object> redisTemplate) {
        super(teamRepository, redisTemplate.opsForHash(), PREFIX);
    }

    @Transactional
    public Optional<Team> update(Long id, String changedTeamName) {
        String key = PREFIX + id.toString();
        Optional<Team> entity = jpaRepository.findById(id);
        if (entity.isEmpty()) {
            return Optional.empty();
        }
        deleteInRedisIfExist(key, entity.get());

        entity.get().update(changedTeamName);

        return Optional.of(jpaRepository.save(entity.get()));
    }
}
