package golfResults.result;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ResultDAO {

    List<Result> findAll();
    Optional<Result> findById(long id);
    Result save(Result result);
    Result update(Result result);
    void deleteById(long id);
    List<Result> findResultsByTournamentName(String tournamentName);
    List<Result> findResultsByUserUsername(String username);
    List<Result> findResultsByTournamentId(Long id);
    List<Result> findResultsByPlayerId(Long id);

}
