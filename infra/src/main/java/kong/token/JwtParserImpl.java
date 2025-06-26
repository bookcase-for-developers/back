package kong.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import kong.token.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtParserImpl {
    private final Key key;

    public JwtParserImpl(@Value("${token.jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isValid(final String token) {
        try {
            getClaim(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            return false;
        }
    }

    public Long getId(final String token) {
        if (isValid(token)) return Long.parseLong(getClaim(token).getBody().getSubject());
        else throw new InvalidTokenException();
    }

    private Jws<Claims> getClaim(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

}
