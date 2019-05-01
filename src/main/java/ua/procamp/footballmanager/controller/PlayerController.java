package ua.procamp.footballmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.procamp.footballmanager.model.dto.PlayerDto;
import ua.procamp.footballmanager.model.entity.Player;
import ua.procamp.footballmanager.model.exception.IncorrectInputException;
import ua.procamp.footballmanager.service.PlayerService;

import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> get(@PathVariable Long id) {
        Player player = playerService.getById(id);
        PlayerDto playerDto = PlayerDto.of(player);
        return ResponseEntity.ok(playerDto);
    }

    @PostMapping
    public ResponseEntity<PlayerDto> save(@RequestBody Player player) {
        Player savedPlayer = playerService.save(player);
        PlayerDto savedPLayerDto = PlayerDto.of(savedPlayer);
        return new ResponseEntity<>(savedPLayerDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PlayerDto> update(@RequestBody Player player) {
        ofNullable(player.getId())
                .orElseThrow(() -> new IncorrectInputException("Player id can't be null"));
        Player updatedPlayer = playerService.update(player);
        PlayerDto updatedPlayerDto = PlayerDto.of(updatedPlayer);
        return ResponseEntity.ok(updatedPlayerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<PlayerDto>> getAll(@PageableDefault Pageable pageable) {
        Page<Player> playersPage = playerService.getAll(pageable);
        Page<PlayerDto> playersDtoPage = playersPage.map(PlayerDto::of);
        return ResponseEntity.ok(playersDtoPage);
    }


    @PostMapping("/team/{teamId}")
    public ResponseEntity<PlayerDto> saveWithTeam(@RequestBody Player player, @PathVariable Long teamId) {
        Player savedPlayer = playerService.saveWithTeam(player, teamId);
        PlayerDto savedPLayerDto = PlayerDto.of(savedPlayer);
        return new ResponseEntity<>(savedPLayerDto, HttpStatus.CREATED);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerDto>> getByTeam(@PathVariable Long teamId) {
        List<Player> players = playerService.getByTeam(teamId);
        List<PlayerDto> playersDto = players.stream().map(PlayerDto::of).collect(toList());
        return ResponseEntity.ok(playersDto);
    }

    @GetMapping("/captain/{teamId}")
    public ResponseEntity<PlayerDto> getCaptainByTeamId(@PathVariable Long teamId) {
        Player captain = playerService.getCaptainByTeamId(teamId);
        PlayerDto captainDto = PlayerDto.of(captain);
        return ResponseEntity.ok(captainDto);
    }
}
