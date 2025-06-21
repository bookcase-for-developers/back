package kong.token.infra;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtParser {
    private final Key key;

    public JwtParser(@Value("${token.jwt.secret-key}") String secretKey) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


//    // 아예 유효하지 않은 토큰인가?
//    public test(String token) {
//        Jws<Claims> test = getClaim(token);
//        Long userId = Long.parseLong(test.getBody().getSubject());
//
//    }

    private Jws<Claims> getClaim(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
