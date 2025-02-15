package golfResults.hole;

public record HoleRequestDTO(
        Integer par,
        Integer strokes,
        Long roundId
) {
}
