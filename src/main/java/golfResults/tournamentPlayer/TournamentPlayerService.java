package golfResults.tournamentPlayer;

import golfResults.user.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TournamentPlayerService {

    private final TournamentPlayerRepository tournamentPlayerRepository;

    public List<TournamentPlayerResponseDTO> getAllTournamentPlayers() {
        List<TournamentPlayerResponseDTO> response = new ArrayList<>();
        setTournamentPlayerDTO(tournamentPlayerRepository.findAll(), response);
        return response;
    }

    public List<TournamentPlayerResponseDTO> getTournamentPlayerByTournamentId(Long id) {
        List<TournamentPlayerResponseDTO> response = new ArrayList<>();
        setTournamentPlayerDTO(tournamentPlayerRepository.findTournamentPlayersByTournamentId(id), response);
        return response;
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

    public void setTournamentPlayerDTO(List<TournamentPlayer> players, List<TournamentPlayerResponseDTO> response) {
        for(TournamentPlayer player : players) {

            TournamentPlayerResponseDTO oneResponsePlayer = new TournamentPlayerResponseDTO(player.getId(), player.getDateJoined(), player.getTournament(),
                    new UserResponseDTO(
                            player.getPlayer().getId(),
                            player.getPlayer().getFirstName(),
                            player.getPlayer().getLastName(),
                            player.getPlayer().getEmail(),
                            player.getPlayer().getUsername(),
                            player.getPlayer().getImage()
                    ));
            response.add(oneResponsePlayer);
        }
    }

}
