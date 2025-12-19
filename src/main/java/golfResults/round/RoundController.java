package golfResults.round;

import golfResults.round.dto.RoundResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin
public class RoundController {

    private final RoundService roundService;

    @GetMapping("/rounds")
    public List<RoundResponseDTO> getAllRounds() {
        return roundService.getAllRounds();
    }

    @GetMapping("/rounds/result/{resultId}")
    public List<RoundResponseDTO> getRoundsByResultId(@PathVariable Long resultId) {
        return roundService.getRoundsByResultId(resultId);
    }

    @GetMapping("/rounds/{id}")
    public RoundResponseDTO getRoundById(@PathVariable Long id) {
        return roundService.getRoundById(id);
    }

}
