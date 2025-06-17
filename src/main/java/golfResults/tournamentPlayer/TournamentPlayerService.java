package golfResults.tournamentPlayer;

import golfResults.exception.MissingParValuesException;
import golfResults.exception.ResourceNotFoundException;
import golfResults.hole.Hole;
import golfResults.hole.HoleRepository;
import golfResults.par.Par;
import golfResults.par.ParRepository;
import golfResults.round.Round;
import golfResults.round.RoundRepository;
import golfResults.tournament.Tournament;
import golfResults.tournament.TournamentRepository;
import golfResults.user.User;
import golfResults.user.UserRepository;
import golfResults.user.UserResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
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
    private final RoundRepository roundRepository;
    private final HoleRepository holeRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final ParRepository parRepository;

    public List<TournamentPlayerResponseDTO> getAllTournamentPlayers() {
        return tournamentPlayerDTOS(tournamentPlayerRepository.findAll(), new ArrayList<>());
    }

    public List<TournamentPlayerResponseDTO> getTournamentPlayerByTournamentId(Long id) {
        return tournamentPlayerDTOS(tournamentPlayerRepository.findTournamentPlayersByTournamentId(id), new ArrayList<>());
    }

    public List<TournamentPlayerResponseDTO> getTournamentPlayerByPlayerId(Long id) {
        return tournamentPlayerDTOS(tournamentPlayerRepository.findTournamentPlayersByPlayerId(id), new ArrayList<>());
    }

    public List<TournamentPlayerResponseDTO> getTournamentsByPlayerUsername(String username) {
        List<TournamentPlayer> tPlayers = tournamentPlayerRepository.findTournamentPlayersByPlayerUsername(username);
        return tournamentPlayerDTOS(tPlayers, new ArrayList<>());
    }

    public TournamentPlayerResponseDTO getTournamentPlayerByResultId(Long resultId) {
        TournamentPlayer tournamentPlayer = tournamentPlayerRepository.findTournamentPlayersByResultId(resultId)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament player with resultId = "+resultId+ " has not found."));
        return tournamentPlayerDTO(tournamentPlayer);
    }

    @Transactional
    public TournamentPlayerResponseDTO createTournamentPlayer(TournamentPlayerRequestDTO tpRequest) {

        if (!tournamentRepository.existsById(tpRequest.tournamentId())) {
            throw new ResourceNotFoundException("Tournament with id = " + tpRequest.tournamentId() + " has not found.");
        }
        if (!userRepository.existsById(tpRequest.playerId())) {
            throw new ResourceNotFoundException("Player with id = " + tpRequest.playerId() + " has not found.");
        }

        Tournament tournament = tournamentRepository.getReferenceById(tpRequest.tournamentId());
        User player = userRepository.getReferenceById(tpRequest.playerId());

        Long resultId = tournamentPlayerRepository.findFirstByOrderByResultIdDesc()
                .map(TournamentPlayer::getResultId)
                .orElse(0L) + 1;

        TournamentPlayer tournamentPlayer = TournamentPlayer.builder()
                .player(player)
                .tournament(tournament)
                .resultId(resultId)
                .dateJoined(new Date())
                .build();

        tournamentPlayerRepository.save(tournamentPlayer);

        createRoundsAndHoles(tournament, tournamentPlayer);

        return tournamentPlayerDTO(tournamentPlayer);
    }

    public Optional<TournamentPlayer> deleteTournamentPlayer(Long tournamentPlayerId) {
        tournamentPlayerRepository.deleteById(tournamentPlayerId);
        return tournamentPlayerRepository.findById(tournamentPlayerId);
    }

    public void createRoundsAndHoles(Tournament tournament, TournamentPlayer tournamentPlayer) {

        int roundNumber = tournament.getRoundNumber();
        int holeNumber = tournament.getHoleNumber();
        List<Par> parList = parRepository.findParsByTournamentIdOrderByIdAsc(tournament.getId());

        if(parList.size() < holeNumber) {
            throw new MissingParValuesException("Cannot register player for tournament because " +
                    "there are not enough par values for the number of holes in the round!");
        }

        for(int i = 0; i < roundNumber; i++) {
            Round round = new Round(null, i+1, tournamentPlayer);
            this.roundRepository.save(round);

            for(int j = 0; j < holeNumber; j++) {
                Hole hole = new Hole(null, j+1, parList.get(j).getPar(), null, null, round);
                this.holeRepository.save(hole);
            }
        }
    }



    public List<TournamentPlayerResponseDTO> tournamentPlayerDTOS(List<TournamentPlayer> players, List<TournamentPlayerResponseDTO> playersDtoList) {
        playersDtoList.addAll(
                players.stream()
                        .map(player -> TournamentPlayerResponseDTO.builder()
                                .tournamentId(player.getTournament().getId())
                                .playerId(player.getPlayer().getId())
                                .dateJoined(player.getDateJoined())
                                .playerUsername(player.getPlayer().getUsername())
                                .tournamentName(player.getTournament().getName())
                                .playerImage(player.getPlayer().getImage())
                                .resultId(player.getResultId())
                                .status(player.getTournament().getStatus())
                                .build())
                        .toList());
        return playersDtoList;
    }

    public TournamentPlayerResponseDTO tournamentPlayerDTO(TournamentPlayer player) {
        return TournamentPlayerResponseDTO.builder()
                .tournamentId(player.getTournament().getId())
                .playerId(player.getPlayer().getId())
                .dateJoined(player.getDateJoined())
                .playerUsername(player.getPlayer().getUsername())
                .tournamentName(player.getTournament().getName())
                .playerImage(player.getPlayer().getImage())
                .resultId(player.getResultId())
                .status(player.getTournament().getStatus())
                .build();
    }

}
