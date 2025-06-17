package golfResults.tournament;

import golfResults.hole.HoleService;
import golfResults.hole.ResultResponseDTO;
import golfResults.tournamentPlayer.TournamentPlayer;
import golfResults.tournamentPlayer.TournamentPlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tournaments")
@AllArgsConstructor
@CrossOrigin
public class TournamentController {

    private final TournamentService tournamentService;
    private final HoleService tournamentPlayerService;

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return new ResponseEntity<>(tournamentService.getAllTournaments(), HttpStatus.OK);
    }

    @GetMapping("/table/{id}")
    public ResponseEntity<List<ResultResponseDTO>> getResult(@PathVariable Long id) {
        return new ResponseEntity<>(tournamentPlayerService.getResultsForTournament(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return new ResponseEntity<>(tournamentService.getTournamentById(id), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Tournament> getTournamentByName(@PathVariable String name) {
        return new ResponseEntity<>(tournamentService.getTournamentByName(name), HttpStatus.OK);
    } // ?

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody TournamentRequestDTO tournament) {
        return new ResponseEntity<>(tournamentService.saveTournament(null, tournament), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody Tournament tournament) {
        //Tournament savedTournament = tournamentService.saveTournament(id, tournament);
        HttpStatus status = tournamentService.doesTournamentExist(id) ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<>(new Tournament(), status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Tournament>> deleteTournament(@PathVariable Long id) {
        return new ResponseEntity<>(tournamentService.deleteTournament(id), HttpStatus.OK);
    }

}
