package golfResults.tournament;

import golfResults.exception.types.ResourceNotFoundException;
import golfResults.par.Par;
import golfResults.par.ParRepository;
import golfResults.tournament.dto.TournamentRequestDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final ParRepository parRepository;

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

    @Transactional
    public Tournament saveTournament(Long id, TournamentRequestDTO tournamentDto) {
        Tournament tournament = mapTournament(id, tournamentDto);

        if (id != null && tournamentRepository.existsById(id)) {
            tournament.setId(id);
        } else {
            tournament.setId(null);
        }
        Tournament savedTournament = tournamentRepository.save(tournament);

        List<Par> pars = tournamentDto.pars().stream()
                .map(parDto -> new Par(null, parDto.par(), savedTournament))
                .toList();

        parRepository.saveAll(pars);

        return savedTournament;
    }

    public void deleteTournament(Long id) {
        tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find and delete employee with id = " + id));
        tournamentRepository.deleteById(id);
    }

    public Tournament mapTournament(Long id, TournamentRequestDTO tournamentDto) {
        return Tournament.builder()
                .id(id)
                .name(tournamentDto.name())
                .location(tournamentDto.location())
                .status(tournamentDto.status())
                .holeNumber(tournamentDto.holeNumber())
                .roundNumber(tournamentDto.roundNumber())
                .description(tournamentDto.description())
                .startDate(tournamentDto.startDate())
                .endDate(tournamentDto.endDate())
                .build();
    }

    public Boolean doesTournamentExist(Long id) {
        return tournamentRepository.existsById(id);
    }
}
