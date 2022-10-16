package com.jisu.repository;

import com.jisu.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, User> userRedisTemplate;
    private final static Duration USER_CACHE_TTL = Duration.ofDays(3);

    public void setUser(User user){
        String key = getKey(user.getUsername());
        log.info("Set user to Redis {} : {}" , key, user);
        userRedisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);
    }

    public Optional<User> getUser(String userName) {
        String key = getKey(userName);
        User user = userRedisTemplate.opsForValue().get(userName);
        log.info("Set user to Redis {} ({})" , key, user);

        return Optional.ofNullable(user);
    }

    /**
     * 레디스에서 키값을 정할떄 prefix를 붙여주면 이걸로 구분할 수 있다.
     * @param userName
     * @return
     */
    private String getKey(String userName) {

        return "USER" + userName;
    }
}
