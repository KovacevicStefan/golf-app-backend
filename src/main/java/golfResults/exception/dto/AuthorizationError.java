package golfResults.exception.dto;

import java.time.LocalDateTime;

public record AuthorizationError(
        int error_code,
        String error_description
) {
}
