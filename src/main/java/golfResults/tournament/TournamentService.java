package golfResults.tournament;

import golfResults.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find tournament with id: " + id));
    }

    public Tournament getTournamentByName(String name) {
        return tournamentRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find tournament with name: " + name));
    }

    public Tournament saveTournament(Long id, Tournament tournament) {
        tournament.setDate(new Date()); // za sada
        if (id != null && tournamentRepository.existsById(id)) {
            tournament.setId(id);
        } else {
            tournament.setId(null);
        }
        return tournamentRepository.save(tournament);
    }

    public Optional<Tournament> deleteTournament(Long id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find and delete employee with id = " + id));
        tournamentRepository.deleteById(id);
        return Optional.ofNullable(tournament);
    }

    public Boolean doesTournamentExist(Long id) {
        return tournamentRepository.existsById(id);
    }
}
