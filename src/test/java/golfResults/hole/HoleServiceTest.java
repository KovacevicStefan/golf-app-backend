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
    HoleRepository holeRepository;

    @Mock
    RoundRepository roundRepository;

    @Mock
    TournamentPlayerRepository tournamentPlayerRepository;

    @InjectMocks
    HoleService holeService;

    @Test
    void shouldModifyHoleAndReturnDto() {
        // given
        Long holeId = 1L;

        Tournament tournament = Tournament.builder()
                .name("Masters")
                .build();

        User player = User.builder()
                .username("stefan")
                .build();

        TournamentPlayer tournamentPlayer = TournamentPlayer.builder()
                .tournament(tournament)
                .player(player)
                .resultId(100L)
                .build();

        Round round = Round.builder()
                .id(10L)
                .tournamentPlayer(tournamentPlayer)
                .build();

        Hole existingHole = Hole.builder()
                .id(holeId)
                .number(1)
                .par(4)
                .strokes(5)
                .score(Score.BOGEY)
                .round(round)
                .build();

        List<HoleRequestDTO> request = new ArrayList<>();
        request.add(new HoleRequestDTO(1L, 4, 5));

        when(holeRepository.findById(holeId))
                .thenReturn(Optional.of(existingHole));

        when(holeRepository.save(any(Hole.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        List<HoleResponseDTO> response = holeService.modifyHoles(request);

        // then
       response.forEach(hole -> {
           assertNotNull(hole);
           assertEquals(holeId, hole.id());
           assertEquals(1, hole.number());
           assertEquals(4, hole.par());
           assertEquals(5, hole.strokes());
           assertEquals(Score.BOGEY, hole.score());
           assertEquals(10L, hole.roundId());
           assertEquals("Masters", hole.tournamentName());
       });

        verify(holeRepository).findById(holeId);
        verify(holeRepository).save(any(Hole.class));
    }

//    @Test
//    void shouldThrowExceptionWhenHoleNotFound() {
//        // given
//        Long holeId = 99L;
//        HoleRequestDTO request = new HoleRequestDTO(99L, 4, 5);
//
//        when(holeRepository.findById(holeId))
//                .thenReturn(Optional.empty());
//
//        // when & then
//        assertThrows(ResourceNotFoundException.class,
//                () -> holeService.modifyHoles(request, holeId));
//
//        verify(holeRepository, never()).save(any());
//    }


}
