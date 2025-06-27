package kong.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kong.token.exception.InvalidUserIdException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtGeneratorImpl {

    private final long accessTokenExpiration;

    private final long refreshTokenExpiration;

    private final Key key;

    public JwtGeneratorImpl(@Value("${token.jwt.secret-key}") String secretKey,
                            @Value("${token.jwt.access-token-expiration}") long accessTokenExpiration,
                            @Value("${token.jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long userId) {
        return createToken(userId, Instant.now(), accessTokenExpiration);
    }

    public String generateRefreshToken(Long userId) {
        return createToken(userId, Instant.now(), refreshTokenExpiration);
    }

    private String createToken(Long userId, Instant issuedAt, long expiration) {
        validateUserId(userId);
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(issuedAt.plusSeconds(expiration)))
                .signWith(key)
                .compact();
    }

    private void validateUserId(Long userId) {
        if (userId == null) {
            throw new InvalidUserIdException();
        }
    }
}
