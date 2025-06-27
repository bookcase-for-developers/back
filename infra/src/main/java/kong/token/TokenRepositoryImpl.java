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
    private static final String PREFIX = "token:";
    private static final int EXPIRE_DAY = 1;

    @Override
    public Optional<Long> findIdByToken(String token) {
        validateToken(token);
        String value = redisTemplate.opsForValue().get(PREFIX + token);
        return Optional.ofNullable(value == null ? null : Long.parseLong(value));
    }

    @Override
    public boolean isExist(String token) {
        validateToken(token);
        return redisTemplate.opsForValue().get(PREFIX + token) != null;
    }

    @Override
    public void setIdByToken(String token, long id) {
        validateToken(token);
        redisTemplate.opsForValue().set(PREFIX + token, String.valueOf(id), EXPIRE_DAY, TimeUnit.DAYS);
    }

    @Override
    public void deleteByToken(String token) {
        validateToken(token);
        redisTemplate.delete(PREFIX + token);

    }

    private void validateToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new InvalidTokenException();
        }
    }
}
