package golfResults.tournamentPlayer;

public record TournamentPlayerRequestDTO(
        Long playerId,
        Long tournamentId
) {
}
