package golfResults.config.emailSender;

public record Email(
        String name,
        String lastName,
        String email,
        String subject,
        String message
) {
}
