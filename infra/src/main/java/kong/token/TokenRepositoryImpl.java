package kong.token;

import kong.token.exception.InvalidTokenException;
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
        validateToken(token);
        String value = redisTemplate.opsForValue().get(token);
        return Optional.ofNullable(value == null ? null : Long.parseLong(value));
    }

    @Override
    public boolean isExist(String token) {
        validateToken(token);
        return redisTemplate.opsForValue().get(token) != null;
    }

    @Override
    public void setIdByToken(String token, long id) {
        validateToken(token);
        redisTemplate.opsForValue().set(token, String.valueOf(id), EXPIRE_DAY, TimeUnit.DAYS);
    }

    @Override
    public void deleteByToken(String token) {
        validateToken(token);
        redisTemplate.delete(token);

    }

    private void validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new InvalidTokenException();
        }
    }
}
