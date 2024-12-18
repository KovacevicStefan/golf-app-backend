package golfResults.tournamentPlayer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TournamentPlayerController {

    private final TournamentPlayerService tournamentPlayerService;

    @GetMapping("/players")
    public ResponseEntity<List<TournamentPlayer>> getTournamentPlayers() {
        return new ResponseEntity<>(tournamentPlayerService.getAllTournamentPlayers(), HttpStatus.OK);
    }

    @GetMapping("/tournament-players/{id}")
    public ResponseEntity<List<TournamentPlayer>> getPlayersByTournamentId(@PathVariable Long id) {
        return new ResponseEntity<>(tournamentPlayerService.getTournamentPlayerByTournamentId(id), HttpStatus.OK);
    }

    @GetMapping("/players/{name}")
    public ResponseEntity<List<TournamentPlayer>> getTournamentPlayers(@PathVariable String name) {
        return new ResponseEntity<>(tournamentPlayerService.getTournamentPlayersByTournamentName(name), HttpStatus.OK);
    }

    @GetMapping("/player-tournaments/{username}")
    public ResponseEntity<List<TournamentPlayer>> getTournamentPlayer(@PathVariable String username) {
        return new ResponseEntity<>(tournamentPlayerService.getTournamentPlayersByPlayerUsername(username), HttpStatus.OK);
    }

    @PostMapping("/players")
    public ResponseEntity<TournamentPlayer> createTournamentPlayer(@RequestBody TournamentPlayer tournamentPlayer) {
        return new ResponseEntity<>(tournamentPlayerService.createTournamentPlayer(tournamentPlayer), HttpStatus.CREATED);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Optional<TournamentPlayer>> deleteTournamentPlayer(@PathVariable Long id) {
        return new ResponseEntity<>(tournamentPlayerService.deleteTournamentPlayer(id), HttpStatus.OK);
    }

}
