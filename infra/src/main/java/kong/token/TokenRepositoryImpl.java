package kong.token;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private static final int EXPIRE_DAY = 1;

    @Override
    public Long getIdFromToken(String token) {
        String value = redisTemplate.opsForValue().get(token);
        return value != null ? Long.parseLong(value) : null;
    }

    @Override
    public boolean isExist(String token) {
        return redisTemplate.opsForValue().get(token) != null;
    }

    @Override
    public void setToken(String token, Long id) {
        redisTemplate.opsForValue().set(token, id.toString(), EXPIRE_DAY, TimeUnit.DAYS);
    }

    @Override
    public void deleteByToken(String token) {
        redisTemplate.delete(token);
    }
}
