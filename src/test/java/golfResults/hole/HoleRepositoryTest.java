package golfResults.hole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class HoleRepositoryTest {

    @Mock
    private HoleRepository holeRepository;

    @InjectMocks
    private HoleRepositoryTest holeRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void itShouldFindRoundScoresByResultId() {
        // Given
        Long resultId = 1L;
        List<Object[]> expectedScores = List.of(
                new Object[]{1, 20}, // Round index 1, total strokes 20
                new Object[]{2, 25}  // Round index 2, total strokes 25
        );

        // When
        when(holeRepository.findRoundScoresByResultId(resultId)).thenReturn(expectedScores);

        // Then
        List<Object[]> actualScores = holeRepository.findRoundScoresByResultId(resultId);
        assertThat(actualScores).isEqualTo(expectedScores);
        assertThat(actualScores).hasSize(2); // Ensure there are two entries
    }

    @Test
    void itShouldFindTotalStrokesSumByResultId() {
        // Given
        Long resultId = 1L;
        Long expectedStrokesSum = 72L; // Total strokes

        // When
        when(holeRepository.findTotalStrokesSumByResultId(resultId)).thenReturn(expectedStrokesSum);

        // Then
        Long actualStrokesSum = holeRepository.findTotalStrokesSumByResultId(resultId);
        assertThat(actualStrokesSum).isEqualTo(expectedStrokesSum);
    }

    @Test
    void itShouldFindTotalParSum() {
        // Given
        Long resultId = 1L;
        Long expectedParSum = 36L; // Total par sum for valid holes

        // When
        when(holeRepository.findTotalParSumByResultId(resultId)).thenReturn(expectedParSum);

        // Then
        Long actualParSum = holeRepository.findTotalParSumByResultId(resultId);
        assertThat(actualParSum).isEqualTo(expectedParSum);
    }

    @Test
    void itShouldFindHolesByRoundId() {
    }
}