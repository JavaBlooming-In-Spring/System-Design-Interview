package system.design.interview;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.transaction.annotation.Transactional;
import system.design.interview.domain.BaseEntity;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class CacheableRepository<E extends BaseEntity, ID> {

    protected final JpaRepository<E, ID> jpaRepository;
    private final HashOperations<String, String, E> redisHashSupport;
    private final String prefix;

    @Transactional
    public E save(E entity) {
        E savedEntity = jpaRepository.save(entity);
        String key = prefix + savedEntity.getId();
        log.info("try to save {" + savedEntity + "} in redis.");
        redisHashSupport.put(key, key, savedEntity);
        log.info("save ok {" + savedEntity + "} in redis.");
        log.info("try to save {" + savedEntity + "} in database.");
        return savedEntity;
    }

    public List<E> findAll() {
        return jpaRepository.findAll();
    }

    @SuppressWarnings("unchecked")
    public Optional<E> findById(ID id) {
        String key = prefix + id.toString();
        E value = redisHashSupport.get(key, key);
        if (value == null) {
            log.info("{" + key + "} is not exist in redis. try to find data in db...");
            Optional<E> entity = jpaRepository.findById(id);
            if (entity.isEmpty()) {
                log.info("{" + key + "} is not exist in db.");
                return Optional.empty();
            }
            log.info("{" + entity + "} is exist in db. insert in redis and return.");
            redisHashSupport.put(key, key, entity.get());
            return entity;
        }
        return Optional.of(value);
    }

    @Transactional
    public void deleteById(ID id) {
        String key = prefix + id.toString();
        E value = redisHashSupport.get(key, key);
        deleteInRedisIfExist(key, value);
        log.info("{" + value + "} will delete from db.");
        jpaRepository.deleteById(id);
    }

    protected void deleteInRedisIfExist(String key, E value) {
        if (value != null) {
            log.info("{" + value + "} is exist in redis. delete from redis.");
            redisHashSupport.delete(key, key);
        }
    }
}
