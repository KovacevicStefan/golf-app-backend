package golfResults.hole.dto;

import java.util.List;

public record ResultResponseDTO(
        Long resultId,
        String username,
        String totalPar,
        List<StrokesDTO> roundResults,
        Long totalStrokes
) {
}
