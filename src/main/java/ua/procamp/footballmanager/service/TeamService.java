package ua.procamp.footballmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.procamp.footballmanager.model.entity.Team;

public interface TeamService {

    Team getById(Long id);

    Team save(Team team);

    Team update(Team team);

    void delete(Long id);

    Page<Team> getAll(Pageable pageable);

    void setCaptain(Long captainId, Long teamId);
}
