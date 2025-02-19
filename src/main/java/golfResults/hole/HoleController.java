package golfResults.hole;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
@AllArgsConstructor
public class HoleController {

    private final HoleService holeService;

    @GetMapping("/holes")
    public ResponseEntity<List<HoleResponseDTO>> getAllHoles() {
        return new ResponseEntity<>(holeService.getAllHoles(), HttpStatus.OK);
    }

    @GetMapping("/holes/round/{roundId}")
    public ResponseEntity<List<HoleResponseDTO>> getHolesByRoundId(@PathVariable Long roundId) {
        return new ResponseEntity<>(holeService.getHolesByRoundId(roundId), HttpStatus.OK);
    }

    @GetMapping("/holes/rounds/{resultId}")
    public ResponseEntity<ResultDTO> getRoundScores(@PathVariable Long resultId) {
        return new ResponseEntity<>(holeService.getResultByResultId(resultId), HttpStatus.OK);
    }

    @PutMapping("/holes/{id}")
    public ResponseEntity<HoleResponseDTO> modifyHole(@RequestBody HoleRequestDTO hole, @PathVariable Long id) {
        return new ResponseEntity<>(holeService.modifyHole(hole, id), HttpStatus.OK);
    }

}
