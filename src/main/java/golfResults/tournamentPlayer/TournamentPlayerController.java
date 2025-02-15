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
@CrossOrigin
public class TournamentPlayerController {

    private final TournamentPlayerService tournamentPlayerService;

    @GetMapping("/players")
    public ResponseEntity<List<TournamentPlayerResponseDTO>> getTournamentPlayers() {
        return new ResponseEntity<>(tournamentPlayerService.getAllTournamentPlayers(), HttpStatus.OK);
    }

    @GetMapping("/players/tournament/{id}")
    public ResponseEntity<List<TournamentPlayerResponseDTO>> getPlayersByTournamentId(@PathVariable Long id) {
        return new ResponseEntity<>(tournamentPlayerService.getTournamentPlayerByTournamentId(id), HttpStatus.OK);
    }

    @GetMapping("/players/player/{id}")
    public ResponseEntity<List<TournamentPlayerResponseDTO>> getPlayersByPlayerId(@PathVariable Long id) {
        return new ResponseEntity<>(tournamentPlayerService.getTournamentPlayerByPlayerId(id), HttpStatus.OK);
    }

    @GetMapping("/players/tournaments/username/{username}")
    public ResponseEntity<List<TournamentPlayerResponseDTO>> getTournamentsByPlayerUsername(@PathVariable String username) {
        return new ResponseEntity<>(tournamentPlayerService.getTournamentsByPlayerUsername(username), HttpStatus.OK);
    }

    @PostMapping("/players")
    public ResponseEntity<TournamentPlayerResponseDTO> createTournamentPlayer(@RequestBody TournamentPlayerRequestDTO tournamentPlayer) {
        return new ResponseEntity<>(tournamentPlayerService.createTournamentPlayer(tournamentPlayer), HttpStatus.CREATED);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Optional<TournamentPlayer>> deleteTournamentPlayer(@PathVariable Long id) {
        return new ResponseEntity<>(tournamentPlayerService.deleteTournamentPlayer(id), HttpStatus.OK);
    }

}
