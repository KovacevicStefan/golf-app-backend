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

    @Query("select sum(coalesce(h.strokes, 0)) as totalSum " +
            "from Hole h " +
            "where h.round.tournamentPlayer.resultId = :resultId ")
    Long findTotalStrokesSum(@Param("resultId") Long resultId);

    @Query("select sum(coalesce(h.par, 0)) as parSum " +
            "from Hole h " +
            "where h.round.tournamentPlayer.resultId = :resultId and h.strokes is not null")
    Long findTotalParSum(@Param("resultId") Long resultId);

    List<Hole> findHolesByRoundId(Long roundId);

}
