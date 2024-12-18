package golfResults.result;

import golfResults.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ResultDAOImpl implements ResultDAO {

    private final EntityManager entityManager;

    @Override
    public List<Result> findAll() {
        return entityManager.createQuery("SELECT r FROM Result r", Result.class).getResultList();
    }

    @Override
    public Optional<Result> findById(long id) {
        return Optional.ofNullable(entityManager.find(Result.class, id));
    }

    @Override
    @Transactional
    public Result save(Result result) {
        entityManager.persist(result);
        return result;
    }

    @Override
    @Transactional
    public Result update(Result result) {
        entityManager.merge(result);
        return result;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        entityManager.remove(entityManager.find(Result.class, id));
    }

    @Override
    public List<Result> findResultsByTournamentName(String tournamentName) {
        String query = "SELECT r FROM Result r " +
                "JOIN r.round.tournamentPlayer tp " +
                "JOIN tp.tournament t " +
                "WHERE t.name = :tournamentName";

        return entityManager.createQuery(query, Result.class)
                .setParameter("tournamentName", tournamentName)
                .getResultList();
    }

    @Override
    public List<Result> findResultsByUserUsername(String username) {
        String query = "SELECT r FROM Result r " +
                "JOIN r.round.tournamentPlayer tp " +
                "JOIN tp.player t " +
                "WHERE t.username = :username";

        return entityManager.createQuery(query, Result.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public List<Result> findResultsByTournamentId(Long id) {
        String query = "SELECT r FROM Result r " +
                "JOIN r.round.tournamentPlayer tp " +
                "join tp.tournament t " +
                "WHERE t.id = :id";

        return entityManager.createQuery(query, Result.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public List<Result> findResultsByPlayerId(Long id) {
        String query = "SELECT r FROM Result r " +
                "JOIN r.round.tournamentPlayer tp " +
                "join tp.player p " +
                "WHERE p.id = :id";

        return entityManager.createQuery(query, Result.class)
                .setParameter("id", id)
                .getResultList();
    }

}
