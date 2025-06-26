package kong.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtParserImpl {
    private final Key key;

    public JwtParserImpl(@Value("${token.jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public boolean isValid(final String token) {
        try {
            getClaim(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getId(final String token) {
        return Long.parseLong(getClaim(token).getBody().getSubject());
    }

    private Jws<Claims> getClaim(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

}
