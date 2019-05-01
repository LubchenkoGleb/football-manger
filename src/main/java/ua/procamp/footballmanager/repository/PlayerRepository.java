package ua.procamp.footballmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.procamp.footballmanager.model.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByTeamId(Long teamId);

    @Query("select t.captain from Team t where t.id = :teamId")
    Optional<Player> findCaptainByTeamId(Long teamId);
}
