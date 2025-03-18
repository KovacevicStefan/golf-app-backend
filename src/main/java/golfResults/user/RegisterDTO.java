package golfResults.user;

public record RegisterDTO(
    String firstName,
    String lastName,
    String email,
    String username,
    String password,
    String image
) {
}
