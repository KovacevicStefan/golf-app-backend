package golfResults.round;

import golfResults.exception.types.ResourceNotFoundException;
import golfResults.round.dto.RoundResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    public List<RoundResponseDTO> getAllRounds() {
        return setDtoList(roundRepository.findAll(), new ArrayList<>());
    }

    public RoundResponseDTO getRoundById(Long id) {
        Round round = this.roundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Round with id = " + id + " has not found."));
        return setDto(round);
    }

    public List<RoundResponseDTO> getRoundsByResultId(Long resultId) {
        return setDtoList(roundRepository.findRoundsByTournamentPlayerResultId(resultId), new ArrayList<>());
    }

    public RoundResponseDTO setDto(Round round) {
        return RoundResponseDTO.builder()
                .id(round.getId())
                .indexNumber(round.getIndexNumber())
                .resultId(round.getTournamentPlayer().getResultId())
                .build();
    }

    public List<RoundResponseDTO> setDtoList(List<Round> roundList, List<RoundResponseDTO> roundDtoList) {
        roundDtoList.addAll(
                roundList.stream()
                        .map(round -> RoundResponseDTO.builder()
                                .id(round.getId())
                                .indexNumber(round.getIndexNumber())
                                .resultId(round.getTournamentPlayer().getResultId())
                                .build())
                        .toList());
        return roundDtoList;
    }

}
