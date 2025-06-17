package golfResults.tournamentPlayer;

import lombok.Builder;

import java.util.Date;

@Builder
public record TournamentPlayerResponseDTO(
        Long playerId,
        Long tournamentId,
        Date dateJoined,
        String playerUsername,
        String tournamentName,
        String playerImage,
        Long resultId,
        Boolean status
) {
}
