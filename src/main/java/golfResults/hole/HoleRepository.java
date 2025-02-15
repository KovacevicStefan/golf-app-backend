package golfResults.hole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoleRepository extends JpaRepository<Hole, Long> {

    @Query("SELECT h.round.indexNumber AS roundIndex, SUM(COALESCE(h.strokes, 0)) AS totalStrokes " +
            "FROM Hole h " +
            "WHERE h.round.tournamentPlayer.resultId = :resultId " +
            "GROUP BY h.round.indexNumber")
    List<Object[]> findRoundScoresByResultId(@Param("resultId") Long resultId);

}
