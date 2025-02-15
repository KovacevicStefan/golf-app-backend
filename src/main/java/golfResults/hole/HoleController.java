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
        return new ResponseEntity<>(this.holeService.getAllHoles(), HttpStatus.OK);
    }

    @PutMapping("/holes/{id}")
    public ResponseEntity<HoleResponseDTO> modifyHole(@RequestBody HoleRequestDTO hole, @PathVariable Long id) {
        return new ResponseEntity<>(this.holeService.modifyHole(hole, id), HttpStatus.OK);
    }

    @GetMapping("/holes/rounds/{resultId}")
    public List<StrokesDTO> getRoundScores(@PathVariable Long resultId) {
        return holeService.getRoundScores(resultId);
    }

}
