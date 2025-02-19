package golfResults.hole;

import java.util.List;

public record ResultDTO(
        String totalPar,
        List<StrokesDTO> roundResults,
        Long totalStrokes
) {
}
