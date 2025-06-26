package kong.token;

public interface TokenRepository {
    public Long getIdFromToken(String token);
    public boolean isExist(String token);
    public void setToken(String token, Long id);
    public void deleteByToken(String token);
}
