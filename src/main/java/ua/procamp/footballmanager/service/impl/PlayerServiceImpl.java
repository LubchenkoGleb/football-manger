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
import ua.procamp.footballmanager.service.PlayerService;

import java.util.List;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    private final TeamRepository teamRepository;

    @Override
    @Transactional(readOnly = true)
    public Player getById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Player with id %s", id)));
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player update(Player player) {
        if (!playerRepository.existsById(player.getId())) {
            throw new ResourceNotFoundException(format("Player with id %s not found", player.getId()));
        }
        return playerRepository.save(player);
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Page<Player> getAll(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    @Override
    public Player saveWithTeam(Player player, Long teamId) {
        Team team = teamRepository.getOne(teamId);
        player.setTeam(team);
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getByTeam(Long teamId) {
        List<Player> players = playerRepository.findAllByTeamId(teamId);
        if (players.isEmpty()) {
            throw new ResourceNotFoundException(format("Team with id %s not found", teamId));
        }
        return players;
    }

    @Override
    public Player getCaptainByTeamId(Long teamId) {
        return playerRepository.findCaptainByTeamId(teamId)
                .orElseThrow(() -> new ResourceNotFoundException(format("Team with id %s not found", teamId)));
    }
}
