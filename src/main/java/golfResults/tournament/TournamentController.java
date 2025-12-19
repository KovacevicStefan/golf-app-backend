package golfResults.tournament;

import golfResults.hole.HoleService;
import golfResults.hole.dto.ResultResponseDTO;
import golfResults.tournament.dto.TournamentRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tournaments")
@AllArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;
    private final HoleService tournamentPlayerService;

    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    @GetMapping("/table/{id}")
    public List<ResultResponseDTO> getResult(@PathVariable Long id) {
        return tournamentPlayerService.getResultsForTournament(id);
    }

    @GetMapping("/{id}")
    public Tournament getTournamentById(@PathVariable Long id) {
        return tournamentService.getTournamentById(id);
    }

    @GetMapping("/name/{name}")
    public Tournament getTournamentByName(@PathVariable String name) {
        return tournamentService.getTournamentByName(name);
    } // ?

    @PostMapping
    public ResponseEntity<Tournament> createTournament(@RequestBody TournamentRequestDTO tournament) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tournamentService.saveTournament(null, tournament));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody TournamentRequestDTO tournament) {
        HttpStatus status = tournamentService.doesTournamentExist(id) ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<>(tournamentService.saveTournament(id, tournament), status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
    }

}
