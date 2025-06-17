package golfResults.hole;

import golfResults.exception.ResourceNotFoundException;
import golfResults.round.Round;
import golfResults.round.RoundRepository;
import golfResults.tournamentPlayer.TournamentPlayer;
import golfResults.tournamentPlayer.TournamentPlayerRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class HoleService {

    private final HoleRepository holeRepository;
    private final RoundRepository roundRepository;
    private final TournamentPlayerRepository tournamentPlayerRepository;

    public List<HoleResponseDTO> getAllHoles() {
        return setDtoList(holeRepository.findAll(), new ArrayList<>());
    }

    public List<HoleResponseDTO> getHolesByRoundId(Long roundId) {
        return setDtoList(holeRepository.findHolesByRoundId(roundId), new ArrayList<>());
    }

    public ResultDTO getResultByResultId(Long resultId) {
        return new ResultDTO(getTotalPar(resultId), getRoundScores(resultId), getTotalSum(resultId));
    }

    public HoleResponseDTO modifyHole(HoleRequestDTO holeDto, Long id) {

        Hole holeOld = this.holeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hole with id = "+id+ "has not found"));

        Hole hole = Hole.builder()
                .id(id)
                .number(holeOld.getNumber())
                .par(holeOld.getPar())
                .strokes(holeDto.strokes())
                .score(setScore(holeDto))
                .round(holeOld.getRound())
                .build();

        this.holeRepository.save(hole);
        return setDto(hole, holeOld.getRound(), id);
    }

    public List<StrokesDTO> getRoundScores(Long resultId) {
        List<Object[]> results = holeRepository.findRoundScoresByResultId(resultId);

        return results.stream()
                .map(result -> new StrokesDTO((Integer) result[0], (Long) result[1]))
                .toList();
    }

    public HoleResponseDTO setDto(Hole hole, Round round, Long id) {
        return HoleResponseDTO.builder()
                .id(id)
                .number(hole.getNumber())
                .par(hole.getPar())
                .strokes(hole.getStrokes())
                .score(hole.getScore())
                .roundId(round.getId())
                .tournamentName(hole.getRound().getTournamentPlayer().getTournament().getName())
                .build();
    }

    public List<HoleResponseDTO> setDtoList(List<Hole> holeList, List<HoleResponseDTO> holeDtoList) {
        holeDtoList.addAll(
                holeList.stream()
                        .map(hole -> HoleResponseDTO.builder()
                                .id(hole.getId())
                                .number(hole.getNumber())
                                .par(hole.getPar())
                                .strokes(hole.getStrokes())
                                .score(hole.getScore())
                                .roundId(hole.getRound().getId())
                                .tournamentName(hole.getRound().getTournamentPlayer().getTournament().getName())
                                .build())
                        .toList());
        return holeDtoList;
    }

    public Long getTotalSum(Long resultId) {
        return holeRepository.findTotalStrokesSumByResultId(resultId);
    }

    public String getTotalPar(Long resultId) {
        Long totalSum = this.getTotalSum(resultId);
        Long totalPar = holeRepository.findTotalParSumByResultId(resultId);
        long result = 0L;
        String num = "/";

        if(totalPar != null) {
            result = totalSum - totalPar;

            if(result == 0) {
                num = Long.toString(0);
            }
        }

        if(result > 0) {
            num = "+" + Long.toString(result);
        } else if(result < 0) {
            num = Long.toString(result);
        }

        return num;
    }

    public Score setScore(HoleRequestDTO hole) {
        Map<Integer, Score> scoreMap = Map.of(-3, Score.DOUBLE_EAGLE, -2, Score.EAGLE, -1, Score.BIRDIE,
                0, Score.PAR, 1, Score.BOGEY, 2, Score.DOUBLE_BOGEY, 3, Score.THREE_PLUS_BOGEY
        );
        return scoreMap.get(hole.strokes() - hole.par());
    }

    public List<ResultResponseDTO> getResultsForTournament(Long tournamentId) {
        List<TournamentPlayer> players = tournamentPlayerRepository.findByTournamentId(tournamentId);

        return players.stream()
                .map(tp -> {
                    Long resultId = tp.getResultId();
                    String username = tp.getPlayer().getUsername();
                    List<StrokesDTO> roundResults = getRoundScores(resultId);
                    Long totalStrokes = getTotalSum(resultId);
                    String totalPar = getTotalPar(resultId);

                    return new ResultResponseDTO(resultId, username, totalPar, roundResults, totalStrokes);
                })
                .toList();
    }

}
