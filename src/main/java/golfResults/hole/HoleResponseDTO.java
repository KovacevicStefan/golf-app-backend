package golfResults.hole;

import golfResults.round.RoundResponseDTO;

import java.util.Date;

public record HoleResponseDTO(
        Long id,
        Integer par,
        Integer strokes,
        Score score,
        Long roundId,
        String tournamentName,
        Date tournamentDate
) {
}
