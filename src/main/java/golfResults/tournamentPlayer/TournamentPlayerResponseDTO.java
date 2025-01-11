package golfResults.tournamentPlayer;

import golfResults.tournament.Tournament;
import golfResults.user.UserResponseDTO;

import java.util.Date;

public record TournamentPlayerResponseDTO(
        Long id,
        Date dateJoined,
        Tournament tournament,
        UserResponseDTO player
) {
}
