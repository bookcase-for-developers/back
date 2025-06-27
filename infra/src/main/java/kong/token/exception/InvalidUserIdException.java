package kong.token.exception;

public class InvalidUserIdException extends RuntimeException {
    public InvalidUserIdException() {
        super("유효하지 않은 유저 ID가 Null입니다.");
    }
}
