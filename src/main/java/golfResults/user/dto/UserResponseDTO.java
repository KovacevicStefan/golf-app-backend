package golfResults.user.dto;

import lombok.Builder;

@Builder
public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String username,
        String image,
        boolean enabled
) {
}
