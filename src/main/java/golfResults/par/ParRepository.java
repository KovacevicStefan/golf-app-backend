package golfResults.par;

import golfResults.tournament.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParRepository extends JpaRepository<Par, Long> {

    List<Par> findParsByTournamentIdOrderByIdAsc(Long id);
}
