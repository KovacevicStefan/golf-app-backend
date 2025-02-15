package golfResults.tournamentPlayer;

import golfResults.tournament.Tournament;
import golfResults.user.UserResponseDTO;

import java.util.Date;

public record TournamentPlayerResponseDTO(
        Long playerId,
        Long tournamentId,
        Date dateJoined,
        String playerUsername,
        String tournamentName,
        String playerImage,
        Long resultId
) {
}
