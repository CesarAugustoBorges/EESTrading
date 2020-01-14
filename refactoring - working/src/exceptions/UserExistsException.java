package exceptions;

public class UserExistsException extends Throwable {
    public UserExistsException(String s) {
        super(s);
    }
}
