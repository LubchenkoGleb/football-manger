package ua.procamp.footballmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.procamp.footballmanager.model.dto.SetCaptainDto;
import ua.procamp.footballmanager.model.dto.TeamDto;
import ua.procamp.footballmanager.model.entity.Team;
import ua.procamp.footballmanager.model.exception.IncorrectInputException;
import ua.procamp.footballmanager.service.TeamService;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> get(@PathVariable Long id) {
        Team team = teamService.getById(id);
        TeamDto teamDto = TeamDto.of(team);
        return ResponseEntity.ok(teamDto);
    }

    @PostMapping
    public ResponseEntity<TeamDto> save(@RequestBody Team team) {
        Team savedTeam = teamService.save(team);
        TeamDto savedPLayerDto = TeamDto.of(savedTeam);
        return new ResponseEntity<>(savedPLayerDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<TeamDto> update(@RequestBody Team team) {
        ofNullable(team.getId())
                .orElseThrow(() -> new IncorrectInputException("Team id can't be null"));
        Team updatedTeam = teamService.update(team);
        TeamDto updatedTeamDto = TeamDto.of(updatedTeam);
        return ResponseEntity.ok(updatedTeamDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<TeamDto>> getAll(@PageableDefault Pageable pageable) {
        Page<Team> teamsPage = teamService.getAll(pageable);
        Page<TeamDto> teamsDtoPage = teamsPage.map(TeamDto::of);
        return ResponseEntity.ok(teamsDtoPage);
    }

    @PutMapping("/captain")
    public ResponseEntity<?> setCaptain(@RequestBody SetCaptainDto setCaptainDto) {
        teamService.setCaptain(setCaptainDto.getCaptainId(), setCaptainDto.getTeamId());
        return ResponseEntity.noContent().build();
    }
}
