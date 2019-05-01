package ua.procamp.footballmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.procamp.footballmanager.model.entity.Player;
import ua.procamp.footballmanager.model.entity.Team;
import ua.procamp.footballmanager.model.exception.ResourceNotFoundException;
import ua.procamp.footballmanager.repository.PlayerRepository;
import ua.procamp.footballmanager.repository.TeamRepository;
import ua.procamp.footballmanager.service.TeamService;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    @Override
    @Transactional(readOnly = true)
    public Team getById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Team with id %s", id)));
    }

    @Override
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public Team update(Team team) {
        if (!teamRepository.existsById(team.getId())) {
            throw new ResourceNotFoundException(format("Team with id %s not found", team.getId()));
        }
        return teamRepository.save(team);
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public Page<Team> getAll(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    @Override
    public void setCaptain(Long captainId, Long teamId) {
        Player captainReference = playerRepository.getOne(captainId);
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new ResourceNotFoundException(format("Team with id %s not found", teamId)));
        team.setCaptain(captainReference);
    }
}
