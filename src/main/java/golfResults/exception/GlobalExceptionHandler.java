package golfResults.exception;

import golfResults.exception.dto.ApiError;
import golfResults.exception.dto.AuthorizationError;
import golfResults.exception.types.MissingParValuesException;
import golfResults.exception.types.ResourceNotFoundException;
import golfResults.exception.types.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ResourceNotFoundException e, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingParValuesException.class)
    public ResponseEntity<ApiError> handleException(MissingParValuesException e, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<AuthorizationError> handleException(AuthorizationException e) {
        AuthorizationError apiError = new AuthorizationError(
                e.getHttpStatus().value(),
                e.getMessage()
        );

        return new ResponseEntity<>(apiError, e.getHttpStatus());
    }

}
