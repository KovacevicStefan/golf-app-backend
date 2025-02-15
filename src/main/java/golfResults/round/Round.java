package golfResults.round;

import golfResults.tournamentPlayer.TournamentPlayer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "round")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer indexNumber;

    @ManyToOne
    @JoinColumn(name = "result_id", referencedColumnName = "result_id", nullable = false)
    private TournamentPlayer tournamentPlayer;

}
