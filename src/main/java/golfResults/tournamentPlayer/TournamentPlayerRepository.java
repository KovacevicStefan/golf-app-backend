package golfResults.tournamentPlayer;

import golfResults.tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentPlayerRepository extends JpaRepository<TournamentPlayer, Long> {

    List<TournamentPlayer> findTournamentPlayersByTournamentName(String tournamentName);
    List<TournamentPlayer> findTournamentPlayersByPlayerUsername(String username);
    List<TournamentPlayer> findTournamentPlayersByTournamentId(Long id);
    Boolean existsByTournamentIdAndPlayerId(Long tournamentId, Long playerId);

}
