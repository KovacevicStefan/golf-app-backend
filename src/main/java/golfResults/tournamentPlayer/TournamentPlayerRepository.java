package golfResults.tournamentPlayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TournamentPlayerRepository extends JpaRepository<TournamentPlayer, Long> {

    Optional<TournamentPlayer> findTournamentPlayersByResultId(Long resultId);
    List<TournamentPlayer> findTournamentPlayersByPlayerUsername(String username);
    List<TournamentPlayer> findTournamentPlayersByTournamentId(Long id);
    List<TournamentPlayer> findTournamentPlayersByPlayerId(Long id);
    Optional<TournamentPlayer> findFirstByOrderByResultIdDesc();

    @Query("SELECT tp FROM TournamentPlayer tp WHERE tp.tournament.id = :tournamentId")
    List<TournamentPlayer> findByTournamentId(@Param("tournamentId") Long tournamentId);


}
