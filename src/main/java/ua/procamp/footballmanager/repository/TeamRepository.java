package ua.procamp.footballmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.procamp.footballmanager.model.entity.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select t from Team t join fetch t.players where t.id = :id")
    Optional<Team> findByIdWithJoinedPlayers(Long id);
}
