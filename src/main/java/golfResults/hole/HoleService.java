package golfResults.hole;

import golfResults.exception.ResourceNotFoundException;
import golfResults.round.Round;
import golfResults.round.RoundRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HoleService {

    private final HoleRepository holeRepository;
    private final RoundRepository roundRepository;

    public List<HoleResponseDTO> getAllHoles() {
        return setDtoList(this.holeRepository.findAll(), new ArrayList<>());
    }

    public HoleResponseDTO modifyHole(HoleRequestDTO holeDto, Long id) {
        Round round = this.roundRepository.findById(holeDto.roundId())
                .orElseThrow(() -> new ResourceNotFoundException("Round with id = "+holeDto.roundId()+ " is not found"));
        Hole hole = new Hole(id, holeDto.par(), holeDto.strokes(), setScore(holeDto), round);
        this.holeRepository.save(hole);
        return setDto(hole, round, id);
    }

    public List<StrokesDTO> getRoundScores(Long resultId) {
        List<Object[]> results = holeRepository.findRoundScoresByResultId(resultId);

        return results.stream()
                .map(result -> new StrokesDTO((Integer) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    public Score setScore(HoleRequestDTO hole) {
        Map<Integer, Score> scoreMap = Map.of(-3, Score.DOUBLE_EAGLE, -2, Score.EAGLE, -1, Score.BIRDIE,
                0, Score.PAR, 1, Score.BOGEY, 2, Score.DOUBLE_BOGEY, 3, Score.THREE_PLUS_BOGEY
        );
        return scoreMap.get(hole.strokes() - hole.par());
    }

    public HoleResponseDTO setDto(Hole hole, Round round, Long id) {
        return new HoleResponseDTO(hole.getId(), hole.getPar(), hole.getStrokes(), hole.getScore(),
                round.getId(), hole.getRound().getTournamentPlayer().getTournament().getName(),
                hole.getRound().getTournamentPlayer().getTournament().getDate());
    }

    public List<HoleResponseDTO> setDtoList(List<Hole> holeList, List<HoleResponseDTO> holeDtoList) {
        for(Hole item : holeList) {
            HoleResponseDTO dtoListItem = new HoleResponseDTO(item.getId(), item.getPar(), item.getStrokes(), item.getScore(), item.getRound().getId(),
                    item.getRound().getTournamentPlayer().getTournament().getName(), item.getRound().getTournamentPlayer().getTournament().getDate());
            holeDtoList.add(dtoListItem);
        }
        return holeDtoList;
    }

}
