package golfResults.exception.types;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthorizationException extends RuntimeException {

    private final HttpStatus httpStatus;

    public AuthorizationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
