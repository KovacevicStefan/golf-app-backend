package golfResults.tournamentPlayer;

import golfResults.tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TournamentPlayerRepository extends JpaRepository<TournamentPlayer, Long> {

    Optional<TournamentPlayer> findTournamentPlayersByResultId(Long resultId);
    List<TournamentPlayer> findTournamentPlayersByPlayerUsername(String username);
    List<TournamentPlayer> findTournamentPlayersByTournamentId(Long id);
    List<TournamentPlayer> findTournamentPlayersByPlayerId(Long id);
    Optional<TournamentPlayer> findFirstByOrderByResultIdDesc();
}
