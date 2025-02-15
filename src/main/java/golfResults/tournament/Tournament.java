package golfResults.tournament;

import golfResults.round.Round;
import golfResults.tournamentPlayer.TournamentPlayer;
import golfResults.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private Date date;
    private Boolean status;
    private String location;
    private String description;
    private Integer roundNumber;
    private Integer holeNumber;

}
