package golfResults.user.dto;

public record LoginResponseDTO(
        String token,
        long expiresIn
) {
}
