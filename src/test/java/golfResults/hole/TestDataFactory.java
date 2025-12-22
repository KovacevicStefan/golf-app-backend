package golfResults.hole;

import golfResults.round.Round;
import golfResults.tournament.Tournament;
import golfResults.tournamentPlayer.TournamentPlayer;
import golfResults.user.User;

public class TestDataFactory {

    public static Hole hole() {

        Tournament tournament = Tournament.builder()
                .name("Test Tournament")
                .build();

        User user = User.builder()
                .username("testuser")
                .build();

        TournamentPlayer tp = TournamentPlayer.builder()
                .resultId(1L) // ✔ OK ako NIJE @Id
                .player(user)
                .tournament(tournament)
                .build();

        Round round = Round.builder()
                .indexNumber(1)
                .tournamentPlayer(tp)
                .build();

        return Hole.builder()
                .number(1)
                .par(4)
                .strokes(5)
                .score(Score.BOGEY)
                .round(round)
                .build();
    }
}
