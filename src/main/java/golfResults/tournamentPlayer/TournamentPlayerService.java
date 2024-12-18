package golfResults.tournamentPlayer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TournamentPlayerService {

    private final TournamentPlayerRepository tournamentPlayerRepository;

    public List<TournamentPlayer> getAllTournamentPlayers() {
        return tournamentPlayerRepository.findAll();
    }

    public List<TournamentPlayer> getTournamentPlayerByTournamentId(Long id) {
        return tournamentPlayerRepository.findTournamentPlayersByTournamentId(id);
    }

    public TournamentPlayer createTournamentPlayer(TournamentPlayer tournamentPlayer) {
        tournamentPlayer.setDateJoined(new Date());
        return tournamentPlayerRepository.save(tournamentPlayer);
    }

    public Optional<TournamentPlayer> deleteTournamentPlayer(Long tournamentPlayerId) {
        tournamentPlayerRepository.deleteById(tournamentPlayerId);
        return tournamentPlayerRepository.findById(tournamentPlayerId);
    } // Nije uredna metoda

    public List<TournamentPlayer> getTournamentPlayersByTournamentName(String tournamentName) {
        return tournamentPlayerRepository.findTournamentPlayersByTournamentName(tournamentName);
    }

    public List<TournamentPlayer> getTournamentPlayersByPlayerUsername(String username) {
        return tournamentPlayerRepository.findTournamentPlayersByPlayerUsername(username);
    }

}
