package golfResults.hole;

import golfResults.hole.dto.HoleRequestDTO;
import golfResults.hole.dto.HoleResponseDTO;
import golfResults.hole.dto.ResultDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holes")
@CrossOrigin
@AllArgsConstructor
public class HoleController {

    private final HoleService holeService;

    @GetMapping
    public List<HoleResponseDTO> getAllHoles() {
        return holeService.getAllHoles();
    }

    @GetMapping("/round/{roundId}")
    public List<HoleResponseDTO> getHolesByRoundId(@PathVariable Long roundId) {
        return holeService.getHolesByRoundId(roundId);
    }

    @GetMapping("/rounds/{resultId}")
    public ResultDTO getRoundScores(@PathVariable Long resultId) {
        return holeService.getResultByResultId(resultId);
    }

    @PutMapping
    public List<HoleResponseDTO> modifyHole(@RequestBody List<HoleRequestDTO> holes) {
        return holeService.modifyHoles(holes);
    }

}
