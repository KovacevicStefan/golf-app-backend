package golfResults.hole;

import lombok.Builder;

import java.util.Date;

@Builder
public record HoleResponseDTO(
        Long id,
        Integer number,
        Integer par,
        Integer strokes,
        Score score,
        Long roundId,
        String tournamentName,
        Date tournamentDate
) {
}
