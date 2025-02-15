package golfResults.tournamentPlayer;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TournamentPlayerId implements Serializable {

    private Long playerId;
    private Long tournamentId;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TournamentPlayerId that = (TournamentPlayerId) o;
        return Objects.equals(playerId, that.playerId) && Objects.equals(tournamentId, that.tournamentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, tournamentId);
    }
}
