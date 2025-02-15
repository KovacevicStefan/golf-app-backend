package golfResults.tournamentPlayer;

import golfResults.tournament.Tournament;
import golfResults.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tournament_player")
public class TournamentPlayer {

    @EmbeddedId
    private TournamentPlayerId id;

    @ManyToOne
    @MapsId("tournamentId")
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id", nullable = false)
    private User player; // ovde obavezno koristi DTO zbog passworda

    @Column(name = "result_id", nullable = false, unique = true)
    private Long resultId;

    private Date dateJoined;

    public TournamentPlayer(User player, Tournament tournament, Long resultId, Date dateJoined) {
        this.id = new TournamentPlayerId(player.getId(), tournament.getId());
        this.player = player;
        this.tournament = tournament;
        this.resultId = resultId;
        this.dateJoined = dateJoined;
    }

}

