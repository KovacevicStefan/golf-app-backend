package golfResults.result;

import golfResults.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ResultService {

    private final ResultDAO resultDAO;

    public List<Result> getAllResults() {
        return resultDAO.findAll();
    }

    public Optional<Result> getResultById(Long id) {
        return Optional.ofNullable(resultDAO.findById(id))
                .orElseThrow(() -> new ResourceNotFoundException("Result with id "+id+ " is not found"));
    }

    public List<Result> getResultByPlayerUsername(String username) {
        List<Result> results = resultDAO.findResultsByUserUsername(username);
        if(results.isEmpty()) {
            throw new ResourceNotFoundException("No results found for player "+username);
        }else {
            return results;
        }
    }

    public List<Result> getResultByTournamentName(String name) {
        List<Result> results = resultDAO.findResultsByTournamentName(name);
        if(results.isEmpty()) {
            throw new ResourceNotFoundException("No results found for player "+name);
        }else {
            return results;
        }
    }

    public Result createResult(Result result) {
        this.scoreEnumSet(result);
        return resultDAO.save(result);
    }

    public Optional<Result> deleteResultById(Long id) {
        Result result = resultDAO.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find and delete result with id = "+id));
        resultDAO.deleteById(id);
        return Optional.ofNullable(result);

    }

    public List<Result> getResultsByTournamentId(Long id) {
        return resultDAO.findResultsByTournamentId(id);
    }

    public List<Result> getResultsByPlayerId(Long id) {
        return resultDAO.findResultsByPlayerId(id);
    }

    public void scoreEnumSet(Result result) {
        Map<Integer, Score> scoreMap = Map.of(-3, Score.DOUBLE_EAGLE, -2, Score.EAGLE, -1, Score.BIRDIE,
                0, Score.PAR, 1, Score.BOGEY, 2, Score.DOUBLE_BOGEY, 3, Score.THREE_PLUS_BOGEY
        );

        if (scoreMap.containsKey(result.getStrokes() - result.getPar())) {
            result.setScore(scoreMap.get(result.getStrokes() - result.getPar()));
        }
    }

}
