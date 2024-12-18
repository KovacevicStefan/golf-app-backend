package golfResults.result;

import golfResults.round.Round;
import golfResults.tournamentPlayer.TournamentPlayer;
import golfResults.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "result")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer holeNumber;
    private Integer par;
    private Integer strokes;

    @Enumerated(EnumType.STRING)
    private Score score;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

    //@ManyToOne
    //@JoinColumn(name = "tournament_player_id", nullable = false)
    //private TournamentPlayer tournamentPlayer;
}
