package golfResults.round;

import golfResults.exception.ResourceNotFoundException;
import golfResults.tournament.Tournament;
import golfResults.tournament.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    public List<Round> getAllRounds() {
        return roundRepository.findAll();
    }

    public Optional<Round> getRoundById(Long id) {
        return Optional.ofNullable(roundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Round with id = " + id + " has not found.")));
    }

    public Round createRound(Round round) {
        round.setId(null);
        return roundRepository.save(round);
    }

}
