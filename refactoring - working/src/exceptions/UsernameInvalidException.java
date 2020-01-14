package exceptions;

public class UsernameInvalidException extends Throwable {
    public UsernameInvalidException(String message) {
        super(message);
    }
}
