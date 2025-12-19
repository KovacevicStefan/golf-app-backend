package golfResults.round.dto;

import lombok.Builder;

@Builder
public record RoundResponseDTO(
        Long id,
        Integer indexNumber,
        Long resultId
) {
}
