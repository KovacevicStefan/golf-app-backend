package golfResults.hole.dto;

import golfResults.hole.Score;
import lombok.Builder;

@Builder
public record HoleResponseDTO(
        Long id,
        Integer number,
        Integer par,
        Integer strokes,
        Score score,
        Long roundId,
        String tournamentName
) {
}
