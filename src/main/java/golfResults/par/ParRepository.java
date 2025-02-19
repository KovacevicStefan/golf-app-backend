package golfResults.par;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParRepository extends JpaRepository<Par, Long> {

    List<Par> findParsByTournamentIdOrderByIdAsc(Long id);
}
