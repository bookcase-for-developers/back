package kong.token;

import java.util.Optional;

public interface TokenRepository {
    public Optional<Long> findIdByToken(String token);
    public boolean isExist(String token);
    public void setIdByToken(String token, long id);
    public void deleteByToken(String token);
}
