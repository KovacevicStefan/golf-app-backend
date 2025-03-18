package golfResults.user;

public record LoginResponseDTO(
        String token,
        long expiresIn
) {
}
