package golfResults.hole.dto;

import java.util.List;

public record ResultDTO(
        String totalPar,
        List<StrokesDTO> roundResults,
        Long totalStrokes
) {
}
