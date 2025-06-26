package kong.token;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private static final int EXPIRE_DAY = 1;

    @Override
    public Optional<Long> findIdByToken(String token) {
        if (!isValidToken(token)) {
            return Optional.empty();
        }
        String value = redisTemplate.opsForValue().get(token);
        return Optional.ofNullable(value == null ? null : Long.parseLong(value));
    }

    @Override
    public boolean isExist(String token) {
        if (!isValidToken(token)) {
            return false;
        }
        return redisTemplate.opsForValue().get(token) != null;
    }

    @Override
    public void setIdByToken(String token, long id) {
        if (isValidToken(token)) {
            redisTemplate.opsForValue().set(token, String.valueOf(id), EXPIRE_DAY, TimeUnit.DAYS);
        }
    }

    @Override
    public void deleteByToken(String token) {
        if (isValidToken(token)) {
            redisTemplate.delete(token);
        }
    }

    private boolean isValidToken(String token) {
        return token != null && !token.trim().isEmpty();
    }
}
