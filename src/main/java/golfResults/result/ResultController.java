package golfResults.result;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/results")
    public ResponseEntity<List<Result>> getAllResults() {
        return new ResponseEntity<>(resultService.getAllResults(), HttpStatus.OK);
    }

    @GetMapping("/results/username/{username}")
    public ResponseEntity<List<Result>> getResultsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(resultService.getResultByPlayerUsername(username), HttpStatus.OK);
    }

    @GetMapping("/results/tournament/{name}")
    public ResponseEntity<List<Result>> getResultsByTournamentName(@PathVariable String name) {
        return new ResponseEntity<>(resultService.getResultByTournamentName(name), HttpStatus.OK);
    }

    @GetMapping("/results/tournament-id/{id}")
    public ResponseEntity<List<Result>> getResultsByTournamentId(@PathVariable Long id) {
        return new ResponseEntity<>(resultService.getResultsByTournamentId(id), HttpStatus.OK);
    }

    @GetMapping("/results/player-id/{id}")
    public ResponseEntity<List<Result>> getResultsByPlayerId(@PathVariable Long id) {
        return new ResponseEntity<>(resultService.getResultsByPlayerId(id), HttpStatus.OK);
    }

    @PostMapping("/results")
    public ResponseEntity<Result> createResult(@RequestBody Result result) {
        return new ResponseEntity<>(resultService.createResult(result), HttpStatus.CREATED);
    }

    @DeleteMapping("/results/{id}")
    public ResponseEntity<Optional<Result>> deleteResult(@PathVariable Long id) {
        return new ResponseEntity<>(resultService.deleteResultById(id), HttpStatus.OK);
    }

}
