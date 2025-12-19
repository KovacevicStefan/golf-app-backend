package golfResults.hole.dto;

public record HoleRequestDTO(
        Long id,
        Integer par,
        Integer strokes
) {
}
