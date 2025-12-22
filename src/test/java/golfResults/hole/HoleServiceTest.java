package golfResults.hole;

import golfResults.exception.types.ResourceNotFoundException;
import golfResults.hole.dto.HoleRequestDTO;
import golfResults.hole.dto.HoleResponseDTO;
import golfResults.round.Round;
import golfResults.round.RoundRepository;
import golfResults.tournament.Tournament;
import golfResults.tournamentPlayer.TournamentPlayer;
import golfResults.tournamentPlayer.TournamentPlayerRepository;
import golfResults.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HoleServiceTest {

    @Mock
    private HoleRepository holeRepository;

    @Mock
    private TournamentPlayerRepository tournamentPlayerRepository;

    @InjectMocks
    private HoleService holeService;

    @Test
    void getAllHoles_shouldReturnDtoList() {
        // given
        Hole hole = TestDataFactory.hole();
        when(holeRepository.findAll()).thenReturn(List.of(hole));

        // when
        List<HoleResponseDTO> result = holeService.getAllHoles();

        // then
        assertEquals(1, result.size());
        assertEquals(hole.getId(), result.get(0).id());
        verify(holeRepository).findAll();
    }

    @Test
    void getHolesByRoundId_shouldReturnOnlyThatRound() {
        // given
        Long roundId = 1L;
        when(holeRepository.findHolesByRoundId(roundId))
                .thenReturn(List.of(TestDataFactory.hole()));

        // when
        List<HoleResponseDTO> result = holeService.getHolesByRoundId(roundId);

        // then
        assertFalse(result.isEmpty());
        verify(holeRepository).findHolesByRoundId(roundId);
    }

    @Test
    void modifyHoles_shouldUpdateAndReturnDto() {
        // given
        Hole oldHole = TestDataFactory.hole();
        HoleRequestDTO request =
                new HoleRequestDTO(oldHole.getId(), oldHole.getPar(), 4);

        when(holeRepository.findById(oldHole.getId()))
                .thenReturn(Optional.of(oldHole));
        when(holeRepository.save(any(Hole.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        // when
        List<HoleResponseDTO> result =
                holeService.modifyHoles(List.of(request));

        // then
        assertEquals(1, result.size());
        assertEquals(4, result.get(0).strokes());
        verify(holeRepository).save(any(Hole.class));
    }

    @Test
    void modifyHoles_shouldThrowIfHoleNotFound() {
        // given
        HoleRequestDTO request = new HoleRequestDTO(99L, 4, 5);
        when(holeRepository.findById(99L)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class,
                () -> holeService.modifyHoles(List.of(request)));
    }

    @Test
    void getTotalPar_shouldReturnFormattedValue() {
        // given
        Long resultId = 1L;
        when(holeRepository.findTotalStrokesSumByResultId(resultId)).thenReturn(72L);
        when(holeRepository.findTotalParSumByResultId(resultId)).thenReturn(70L);

        // when
        String result = holeService.getTotalPar(resultId);

        // then
        assertEquals("+2", result);
    }

}
