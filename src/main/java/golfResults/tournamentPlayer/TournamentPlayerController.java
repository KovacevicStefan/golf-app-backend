package golfResults.tournamentPlayer;

import golfResults.tournamentPlayer.dto.TournamentPlayerRequestDTO;
import golfResults.tournamentPlayer.dto.TournamentPlayerResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@AllArgsConstructor
@CrossOrigin
public class TournamentPlayerController {

    private final TournamentPlayerService tournamentPlayerService;

    @GetMapping
    public List<TournamentPlayerResponseDTO> getAll() {
        return tournamentPlayerService.getAllTournamentPlayers();
    }

    @GetMapping("/tournament/{id}")
    public List<TournamentPlayerResponseDTO> getByTournamentId(@PathVariable Long id) {
        return tournamentPlayerService.getTournamentPlayerByTournamentId(id);
    }

    @GetMapping("/player/{id}")
    public List<TournamentPlayerResponseDTO> getByPlayerId(@PathVariable Long id) {
        return tournamentPlayerService.getTournamentPlayerByPlayerId(id);
    }

    @GetMapping("/tournaments/username/{username}")
    public List<TournamentPlayerResponseDTO> getTournamentsByUsername(@PathVariable String username) {
        return tournamentPlayerService.getTournamentsByPlayerUsername(username);
    }

    @GetMapping("/result/{resultId}")
    public TournamentPlayerResponseDTO getByResultId(@PathVariable Long resultId) {
        return tournamentPlayerService.getTournamentPlayerByResultId(resultId);
    }

    @PostMapping
    public ResponseEntity<TournamentPlayerResponseDTO> create(@RequestBody TournamentPlayerRequestDTO req) {
        TournamentPlayerResponseDTO created = tournamentPlayerService.createTournamentPlayer(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long tournamentId, @RequestParam Long playerId) {
        tournamentPlayerService.deleteTournamentPlayer(tournamentId, playerId);
    }

}
