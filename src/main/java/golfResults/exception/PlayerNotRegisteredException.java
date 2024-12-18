package golfResults.exception;

public class PlayerNotRegisteredException extends IllegalArgumentException {

    public PlayerNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlayerNotRegisteredException(String s) {
        super(s);
    }
}
