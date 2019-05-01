package ua.procamp.footballmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.procamp.footballmanager.model.entity.Player;

import java.util.List;

public interface PlayerService {

    Player getById(Long id);

    Player save(Player player);

    Player update(Player player);

    void delete(Long id);

    Page<Player> getAll(Pageable pageable);

    Player saveWithTeam(Player player, Long teamId);

    List<Player> getByTeam(Long id);

    Player getCaptainByTeamId(Long teamId);
}
