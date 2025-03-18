package golfResults.user;

public record VerifyDTO(
        String email,
        String verificationCode
) {
}
