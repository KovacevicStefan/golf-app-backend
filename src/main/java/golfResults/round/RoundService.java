package golfResults.round;

import golfResults.exception.ResourceNotFoundException;
import golfResults.tournament.Tournament;
import golfResults.tournament.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    public List<RoundResponseDTO> getAllRounds() {
        List<RoundResponseDTO> dtoList = new ArrayList<>();
        setDtoList(roundRepository.findAll(), dtoList);
        return dtoList;
    }

    public RoundResponseDTO getRoundById(Long id) {
        Round round = this.roundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Round with id = " + id + " has not found."));
        return setDto(round);
    }

    public RoundResponseDTO setDto(Round round) {
        return new RoundResponseDTO(round.getId(), round.getIndexNumber(), round.getTournamentPlayer().getResultId());
    }

    public void setDtoList(List<Round> roundList, List<RoundResponseDTO> dtoList) {
        for(Round item : roundList) {
            RoundResponseDTO dtoListItem = new RoundResponseDTO(item.getId(), item.getIndexNumber(), item.getTournamentPlayer().getResultId());
            dtoList.add(dtoListItem);
        }
    }

}
