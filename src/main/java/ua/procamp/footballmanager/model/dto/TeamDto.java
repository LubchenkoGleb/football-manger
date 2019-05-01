package ua.procamp.footballmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.procamp.footballmanager.model.entity.Team;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {

    private Long id;

    private String name;

    private PlayerDto captain;

    private List<PlayerDto> players;

    public static TeamDto of(Team team) {
        return new TeamDto(
                team.getId(),
                team.getName(),
                PlayerDto.of(team.getCaptain()),
                team.getPlayers().stream().map(PlayerDto::of).collect(toList())
        );
    }
}
